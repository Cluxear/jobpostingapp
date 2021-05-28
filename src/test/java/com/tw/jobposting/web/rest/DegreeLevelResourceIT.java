package com.tw.jobposting.web.rest;

import com.tw.jobposting.JobpostingApp;
import com.tw.jobposting.config.TestSecurityConfiguration;
import com.tw.jobposting.domain.DegreeLevel;
import com.tw.jobposting.repository.DegreeLevelRepository;
import com.tw.jobposting.service.DegreeLevelService;
import com.tw.jobposting.service.dto.DegreeLevelDTO;
import com.tw.jobposting.service.mapper.DegreeLevelMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DegreeLevelResource} REST controller.
 */
@SpringBootTest(classes = { JobpostingApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DegreeLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DegreeLevelRepository degreeLevelRepository;

    @Autowired
    private DegreeLevelMapper degreeLevelMapper;

    @Autowired
    private DegreeLevelService degreeLevelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDegreeLevelMockMvc;

    private DegreeLevel degreeLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreeLevel createEntity(EntityManager em) {
        DegreeLevel degreeLevel = new DegreeLevel()
            .name(DEFAULT_NAME);
        return degreeLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreeLevel createUpdatedEntity(EntityManager em) {
        DegreeLevel degreeLevel = new DegreeLevel()
            .name(UPDATED_NAME);
        return degreeLevel;
    }

    @BeforeEach
    public void initTest() {
        degreeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDegreeLevel() throws Exception {
        int databaseSizeBeforeCreate = degreeLevelRepository.findAll().size();
        // Create the DegreeLevel
        DegreeLevelDTO degreeLevelDTO = degreeLevelMapper.toDto(degreeLevel);
        restDegreeLevelMockMvc.perform(post("/api/degree-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the DegreeLevel in the database
        List<DegreeLevel> degreeLevelList = degreeLevelRepository.findAll();
        assertThat(degreeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        DegreeLevel testDegreeLevel = degreeLevelList.get(degreeLevelList.size() - 1);
        assertThat(testDegreeLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDegreeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreeLevelRepository.findAll().size();

        // Create the DegreeLevel with an existing ID
        degreeLevel.setId(1L);
        DegreeLevelDTO degreeLevelDTO = degreeLevelMapper.toDto(degreeLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreeLevelMockMvc.perform(post("/api/degree-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DegreeLevel in the database
        List<DegreeLevel> degreeLevelList = degreeLevelRepository.findAll();
        assertThat(degreeLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDegreeLevels() throws Exception {
        // Initialize the database
        degreeLevelRepository.saveAndFlush(degreeLevel);

        // Get all the degreeLevelList
        restDegreeLevelMockMvc.perform(get("/api/degree-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degreeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDegreeLevel() throws Exception {
        // Initialize the database
        degreeLevelRepository.saveAndFlush(degreeLevel);

        // Get the degreeLevel
        restDegreeLevelMockMvc.perform(get("/api/degree-levels/{id}", degreeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(degreeLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDegreeLevel() throws Exception {
        // Get the degreeLevel
        restDegreeLevelMockMvc.perform(get("/api/degree-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDegreeLevel() throws Exception {
        // Initialize the database
        degreeLevelRepository.saveAndFlush(degreeLevel);

        int databaseSizeBeforeUpdate = degreeLevelRepository.findAll().size();

        // Update the degreeLevel
        DegreeLevel updatedDegreeLevel = degreeLevelRepository.findById(degreeLevel.getId()).get();
        // Disconnect from session so that the updates on updatedDegreeLevel are not directly saved in db
        em.detach(updatedDegreeLevel);
        updatedDegreeLevel
            .name(UPDATED_NAME);
        DegreeLevelDTO degreeLevelDTO = degreeLevelMapper.toDto(updatedDegreeLevel);

        restDegreeLevelMockMvc.perform(put("/api/degree-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeLevelDTO)))
            .andExpect(status().isOk());

        // Validate the DegreeLevel in the database
        List<DegreeLevel> degreeLevelList = degreeLevelRepository.findAll();
        assertThat(degreeLevelList).hasSize(databaseSizeBeforeUpdate);
        DegreeLevel testDegreeLevel = degreeLevelList.get(degreeLevelList.size() - 1);
        assertThat(testDegreeLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDegreeLevel() throws Exception {
        int databaseSizeBeforeUpdate = degreeLevelRepository.findAll().size();

        // Create the DegreeLevel
        DegreeLevelDTO degreeLevelDTO = degreeLevelMapper.toDto(degreeLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegreeLevelMockMvc.perform(put("/api/degree-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DegreeLevel in the database
        List<DegreeLevel> degreeLevelList = degreeLevelRepository.findAll();
        assertThat(degreeLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDegreeLevel() throws Exception {
        // Initialize the database
        degreeLevelRepository.saveAndFlush(degreeLevel);

        int databaseSizeBeforeDelete = degreeLevelRepository.findAll().size();

        // Delete the degreeLevel
        restDegreeLevelMockMvc.perform(delete("/api/degree-levels/{id}", degreeLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DegreeLevel> degreeLevelList = degreeLevelRepository.findAll();
        assertThat(degreeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
