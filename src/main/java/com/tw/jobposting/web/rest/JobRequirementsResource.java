package com.tw.jobposting.web.rest;

import com.tw.jobposting.domain.JobRequirements;
import com.tw.jobposting.repository.JobRequirementsRepository;
import com.tw.jobposting.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.jobposting.domain.JobRequirements}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobRequirementsResource {

    private final Logger log = LoggerFactory.getLogger(JobRequirementsResource.class);

    private static final String ENTITY_NAME = "jobpostingJobRequirements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobRequirementsRepository jobRequirementsRepository;

    public JobRequirementsResource(JobRequirementsRepository jobRequirementsRepository) {
        this.jobRequirementsRepository = jobRequirementsRepository;
    }

    /**
     * {@code POST  /job-requirements} : Create a new jobRequirements.
     *
     * @param jobRequirements the jobRequirements to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobRequirements, or with status {@code 400 (Bad Request)} if the jobRequirements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-requirements")
    public ResponseEntity<JobRequirements> createJobRequirements(@RequestBody JobRequirements jobRequirements) throws URISyntaxException {
        log.debug("REST request to save JobRequirements : {}", jobRequirements);
        if (jobRequirements.getId() != null) {
            throw new BadRequestAlertException("A new jobRequirements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobRequirements result = jobRequirementsRepository.save(jobRequirements);
        return ResponseEntity.created(new URI("/api/job-requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-requirements} : Updates an existing jobRequirements.
     *
     * @param jobRequirements the jobRequirements to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobRequirements,
     * or with status {@code 400 (Bad Request)} if the jobRequirements is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobRequirements couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-requirements")
    public ResponseEntity<JobRequirements> updateJobRequirements(@RequestBody JobRequirements jobRequirements) throws URISyntaxException {
        log.debug("REST request to update JobRequirements : {}", jobRequirements);
        if (jobRequirements.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobRequirements result = jobRequirementsRepository.save(jobRequirements);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobRequirements.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-requirements} : get all the jobRequirements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobRequirements in body.
     */
    @GetMapping("/job-requirements")
    public List<JobRequirements> getAllJobRequirements() {
        log.debug("REST request to get all JobRequirements");
        return jobRequirementsRepository.findAll();
    }

    /**
     * {@code GET  /job-requirements/:id} : get the "id" jobRequirements.
     *
     * @param id the id of the jobRequirements to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobRequirements, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-requirements/{id}")
    public ResponseEntity<JobRequirements> getJobRequirements(@PathVariable Long id) {
        log.debug("REST request to get JobRequirements : {}", id);
        Optional<JobRequirements> jobRequirements = jobRequirementsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobRequirements);
    }

    /**
     * {@code DELETE  /job-requirements/:id} : delete the "id" jobRequirements.
     *
     * @param id the id of the jobRequirements to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-requirements/{id}")
    public ResponseEntity<Void> deleteJobRequirements(@PathVariable Long id) {
        log.debug("REST request to delete JobRequirements : {}", id);
        jobRequirementsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
