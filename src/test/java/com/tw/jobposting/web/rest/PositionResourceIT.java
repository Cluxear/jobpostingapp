package com.tw.jobposting.web.rest;

import com.tw.jobposting.JobpostingApp;
import com.tw.jobposting.config.TestSecurityConfiguration;
import com.tw.jobposting.domain.Position;
import com.tw.jobposting.repository.PositionRepository;
import com.tw.jobposting.service.PositionService;
import com.tw.jobposting.service.dto.PositionDTO;
import com.tw.jobposting.service.mapper.PositionMapper;

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
 * Integration tests for the {@link PositionResource} REST controller.
 */
@SpringBootTest(classes = { JobpostingApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PositionResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_MIN_SALARY = 1L;
    private static final Long UPDATED_MIN_SALARY = 2L;

    private static final Long DEFAULT_MAX_SALARY = 1L;
    private static final Long UPDATED_MAX_SALARY = 2L;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPositionMockMvc;

    private Position position;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createEntity(EntityManager em) {
        Position position = new Position()
            .jobTitle(DEFAULT_JOB_TITLE)
            .minSalary(DEFAULT_MIN_SALARY)
            .maxSalary(DEFAULT_MAX_SALARY);
        return position;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createUpdatedEntity(EntityManager em) {
        Position position = new Position()
            .jobTitle(UPDATED_JOB_TITLE)
            .minSalary(UPDATED_MIN_SALARY)
            .maxSalary(UPDATED_MAX_SALARY);
        return position;
    }

    @BeforeEach
    public void initTest() {
        position = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosition() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();
        // Create the Position
        PositionDTO positionDTO = positionMapper.toDto(position);
        restPositionMockMvc.perform(post("/api/positions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
            .andExpect(status().isCreated());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate + 1);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testPosition.getMinSalary()).isEqualTo(DEFAULT_MIN_SALARY);
        assertThat(testPosition.getMaxSalary()).isEqualTo(DEFAULT_MAX_SALARY);
    }

    @Test
    @Transactional
    public void createPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position with an existing ID
        position.setId(1L);
        PositionDTO positionDTO = positionMapper.toDto(position);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionMockMvc.perform(post("/api/positions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPositions() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList
        restPositionMockMvc.perform(get("/api/positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].minSalary").value(hasItem(DEFAULT_MIN_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].maxSalary").value(hasItem(DEFAULT_MAX_SALARY.intValue())));
    }
    
    @Test
    @Transactional
    public void getPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", position.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(position.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.minSalary").value(DEFAULT_MIN_SALARY.intValue()))
            .andExpect(jsonPath("$.maxSalary").value(DEFAULT_MAX_SALARY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position
        Position updatedPosition = positionRepository.findById(position.getId()).get();
        // Disconnect from session so that the updates on updatedPosition are not directly saved in db
        em.detach(updatedPosition);
        updatedPosition
            .jobTitle(UPDATED_JOB_TITLE)
            .minSalary(UPDATED_MIN_SALARY)
            .maxSalary(UPDATED_MAX_SALARY);
        PositionDTO positionDTO = positionMapper.toDto(updatedPosition);

        restPositionMockMvc.perform(put("/api/positions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
            .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testPosition.getMinSalary()).isEqualTo(UPDATED_MIN_SALARY);
        assertThat(testPosition.getMaxSalary()).isEqualTo(UPDATED_MAX_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Create the Position
        PositionDTO positionDTO = positionMapper.toDto(position);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionMockMvc.perform(put("/api/positions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeDelete = positionRepository.findAll().size();

        // Delete the position
        restPositionMockMvc.perform(delete("/api/positions/{id}", position.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
