package com.tw.jobposting.web.rest;

import com.tw.jobposting.JobpostingApp;
import com.tw.jobposting.config.TestSecurityConfiguration;
import com.tw.jobposting.domain.JobPost;
import com.tw.jobposting.repository.JobPostRepository;
import com.tw.jobposting.service.JobPostService;
import com.tw.jobposting.service.dto.JobPostDTO;
import com.tw.jobposting.service.mapper.JobPostMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tw.jobposting.domain.enumeration.LocationType;
import com.tw.jobposting.domain.enumeration.EmploymentType;
/**
 * Integration tests for the {@link JobPostResource} REST controller.
 */
@SpringBootTest(classes = { JobpostingApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class JobPostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_ESTIMATED_SALARY = 1D;
    private static final Double UPDATED_ESTIMATED_SALARY = 2D;

    private static final LocationType DEFAULT_TYPE = LocationType.REMOTE;
    private static final LocationType UPDATED_TYPE = LocationType.ON_SITE;

    private static final EmploymentType DEFAULT_EMPLOYMENT_TYPE = EmploymentType.FULL_TIME;
    private static final EmploymentType UPDATED_EMPLOYMENT_TYPE = EmploymentType.PART_TIME;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private JobPostRepository jobpostRepository;

    @Autowired
    private JobPostMapper jobpostMapper;

    @Autowired
    private JobPostService jobpostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobpostMockMvc;

    private JobPost jobpost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPost createEntity(EntityManager em) {
        JobPost jobpost = new JobPost()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .estimatedSalary(DEFAULT_ESTIMATED_SALARY)
            .type(DEFAULT_TYPE)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedAt(DEFAULT_MODIFIED_AT);
        return jobpost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPost createUpdatedEntity(EntityManager em) {
        JobPost jobpost = new JobPost()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .estimatedSalary(UPDATED_ESTIMATED_SALARY)
            .type(UPDATED_TYPE)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedAt(UPDATED_MODIFIED_AT);
        return jobpost;
    }

    @BeforeEach
    public void initTest() {
        jobpost = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobpost() throws Exception {
        int databaseSizeBeforeCreate = jobpostRepository.findAll().size();
        // Create the Jobpost
        JobPostDTO jobpostDTO = jobpostMapper.toDto(jobpost);
        restJobpostMockMvc.perform(post("/api/jobposts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobpostDTO)))
            .andExpect(status().isCreated());

        // Validate the Jobpost in the database
        List<JobPost> jobpostList = jobpostRepository.findAll();
        assertThat(jobpostList).hasSize(databaseSizeBeforeCreate + 1);
        JobPost testJobpost = jobpostList.get(jobpostList.size() - 1);
        assertThat(testJobpost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobpost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobpost.getEstimatedSalary()).isEqualTo(DEFAULT_ESTIMATED_SALARY);
        assertThat(testJobpost.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJobpost.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testJobpost.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobpost.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createJobpostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobpostRepository.findAll().size();

        // Create the Jobpost with an existing ID
        jobpost.setId(1L);
        JobPostDTO jobpostDTO = jobpostMapper.toDto(jobpost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobpostMockMvc.perform(post("/api/jobposts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobpostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jobpost in the database
        List<JobPost> jobpostList = jobpostRepository.findAll();
        assertThat(jobpostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobposts() throws Exception {
        // Initialize the database
        jobpostRepository.saveAndFlush(jobpost);

        // Get all the jobpostList
        restJobpostMockMvc.perform(get("/api/jobposts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobpost.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].estimatedSalary").value(hasItem(DEFAULT_ESTIMATED_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getJobpost() throws Exception {
        // Initialize the database
        jobpostRepository.saveAndFlush(jobpost);

        // Get the jobpost
        restJobpostMockMvc.perform(get("/api/jobposts/{id}", jobpost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobpost.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.estimatedSalary").value(DEFAULT_ESTIMATED_SALARY.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJobpost() throws Exception {
        // Get the jobpost
        restJobpostMockMvc.perform(get("/api/jobposts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobpost() throws Exception {
        // Initialize the database
        jobpostRepository.saveAndFlush(jobpost);

        int databaseSizeBeforeUpdate = jobpostRepository.findAll().size();

        // Update the jobpost
        JobPost updatedJobpost = jobpostRepository.findById(jobpost.getId()).get();
        // Disconnect from session so that the updates on updatedJobpost are not directly saved in db
        em.detach(updatedJobpost);
        updatedJobpost
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .estimatedSalary(UPDATED_ESTIMATED_SALARY)
            .type(UPDATED_TYPE)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedAt(UPDATED_MODIFIED_AT);
        JobPostDTO jobpostDTO = jobpostMapper.toDto(updatedJobpost);

        restJobpostMockMvc.perform(put("/api/jobposts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobpostDTO)))
            .andExpect(status().isOk());

        // Validate the Jobpost in the database
        List<JobPost> jobpostList = jobpostRepository.findAll();
        assertThat(jobpostList).hasSize(databaseSizeBeforeUpdate);
        JobPost testJobpost = jobpostList.get(jobpostList.size() - 1);
        assertThat(testJobpost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobpost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobpost.getEstimatedSalary()).isEqualTo(UPDATED_ESTIMATED_SALARY);
        assertThat(testJobpost.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJobpost.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testJobpost.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobpost.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingJobpost() throws Exception {
        int databaseSizeBeforeUpdate = jobpostRepository.findAll().size();

        // Create the Jobpost
        JobPostDTO jobpostDTO = jobpostMapper.toDto(jobpost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobpostMockMvc.perform(put("/api/jobposts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobpostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jobpost in the database
        List<JobPost> jobpostList = jobpostRepository.findAll();
        assertThat(jobpostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobpost() throws Exception {
        // Initialize the database
        jobpostRepository.saveAndFlush(jobpost);

        int databaseSizeBeforeDelete = jobpostRepository.findAll().size();

        // Delete the jobpost
        restJobpostMockMvc.perform(delete("/api/jobposts/{id}", jobpost.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobPost> jobpostList = jobpostRepository.findAll();
        assertThat(jobpostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
