package com.tw.jobposting.service;

import com.tw.jobposting.domain.JobPost;
import com.tw.jobposting.service.dto.JobPostDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link JobPost}.
 */
public interface JobPostService {

    /**
     * Save a jobpost.
     *
     * @param jobpostDTO the entity to save.
     * @return the persisted entity.
     */
    JobPostDTO save(JobPostDTO jobpostDTO);

    /**
     * Get all the jobposts.
     *
     * @return the list of entities.
     */
    List<JobPostDTO> findAll();


    /**
     * Get the "id" jobpost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobPostDTO> findOne(Long id);

    /**
     * Delete the "id" jobpost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
