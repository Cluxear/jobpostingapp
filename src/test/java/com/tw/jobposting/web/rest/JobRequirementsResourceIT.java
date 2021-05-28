package com.tw.jobposting.web.rest;

import com.tw.jobposting.JobpostingApp;
import com.tw.jobposting.config.TestSecurityConfiguration;
import com.tw.jobposting.domain.JobRequirements;
import com.tw.jobposting.repository.JobRequirementsRepository;

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
 * Integration tests for the {@link JobRequirementsResource} REST controller.
 */
@SpringBootTest(classes = { JobpostingApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class JobRequirementsResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private JobRequirementsRepository jobRequirementsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobRequirementsMockMvc;

    private JobRequirements jobRequirements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobRequirements createEntity(EntityManager em) {
        JobRequirements jobRequirements = new JobRequirements()
            .content(DEFAULT_CONTENT);
        return jobRequirements;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobRequirements createUpdatedEntity(EntityManager em) {
        JobRequirements jobRequirements = new JobRequirements()
            .content(UPDATED_CONTENT);
        return jobRequirements;
    }

    @BeforeEach
    public void initTest() {
        jobRequirements = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobRequirements() throws Exception {
        int databaseSizeBeforeCreate = jobRequirementsRepository.findAll().size();
        // Create the JobRequirements
        restJobRequirementsMockMvc.perform(post("/api/job-requirements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequirements)))
            .andExpect(status().isCreated());

        // Validate the JobRequirements in the database
        List<JobRequirements> jobRequirementsList = jobRequirementsRepository.findAll();
        assertThat(jobRequirementsList).hasSize(databaseSizeBeforeCreate + 1);
        JobRequirements testJobRequirements = jobRequirementsList.get(jobRequirementsList.size() - 1);
        assertThat(testJobRequirements.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createJobRequirementsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRequirementsRepository.findAll().size();

        // Create the JobRequirements with an existing ID
        jobRequirements.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobRequirementsMockMvc.perform(post("/api/job-requirements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequirements)))
            .andExpect(status().isBadRequest());

        // Validate the JobRequirements in the database
        List<JobRequirements> jobRequirementsList = jobRequirementsRepository.findAll();
        assertThat(jobRequirementsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobRequirements() throws Exception {
        // Initialize the database
        jobRequirementsRepository.saveAndFlush(jobRequirements);

        // Get all the jobRequirementsList
        restJobRequirementsMockMvc.perform(get("/api/job-requirements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobRequirements.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getJobRequirements() throws Exception {
        // Initialize the database
        jobRequirementsRepository.saveAndFlush(jobRequirements);

        // Get the jobRequirements
        restJobRequirementsMockMvc.perform(get("/api/job-requirements/{id}", jobRequirements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobRequirements.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }
    @Test
    @Transactional
    public void getNonExistingJobRequirements() throws Exception {
        // Get the jobRequirements
        restJobRequirementsMockMvc.perform(get("/api/job-requirements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobRequirements() throws Exception {
        // Initialize the database
        jobRequirementsRepository.saveAndFlush(jobRequirements);

        int databaseSizeBeforeUpdate = jobRequirementsRepository.findAll().size();

        // Update the jobRequirements
        JobRequirements updatedJobRequirements = jobRequirementsRepository.findById(jobRequirements.getId()).get();
        // Disconnect from session so that the updates on updatedJobRequirements are not directly saved in db
        em.detach(updatedJobRequirements);
        updatedJobRequirements
            .content(UPDATED_CONTENT);

        restJobRequirementsMockMvc.perform(put("/api/job-requirements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobRequirements)))
            .andExpect(status().isOk());

        // Validate the JobRequirements in the database
        List<JobRequirements> jobRequirementsList = jobRequirementsRepository.findAll();
        assertThat(jobRequirementsList).hasSize(databaseSizeBeforeUpdate);
        JobRequirements testJobRequirements = jobRequirementsList.get(jobRequirementsList.size() - 1);
        assertThat(testJobRequirements.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingJobRequirements() throws Exception {
        int databaseSizeBeforeUpdate = jobRequirementsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobRequirementsMockMvc.perform(put("/api/job-requirements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequirements)))
            .andExpect(status().isBadRequest());

        // Validate the JobRequirements in the database
        List<JobRequirements> jobRequirementsList = jobRequirementsRepository.findAll();
        assertThat(jobRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobRequirements() throws Exception {
        // Initialize the database
        jobRequirementsRepository.saveAndFlush(jobRequirements);

        int databaseSizeBeforeDelete = jobRequirementsRepository.findAll().size();

        // Delete the jobRequirements
        restJobRequirementsMockMvc.perform(delete("/api/job-requirements/{id}", jobRequirements.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobRequirements> jobRequirementsList = jobRequirementsRepository.findAll();
        assertThat(jobRequirementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
