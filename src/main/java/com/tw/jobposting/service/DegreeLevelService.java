package com.tw.jobposting.service;

import com.tw.jobposting.service.dto.DegreeLevelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.jobposting.domain.DegreeLevel}.
 */
public interface DegreeLevelService {

    /**
     * Save a degreeLevel.
     *
     * @param degreeLevelDTO the entity to save.
     * @return the persisted entity.
     */
    DegreeLevelDTO save(DegreeLevelDTO degreeLevelDTO);

    /**
     * Get all the degreeLevels.
     *
     * @return the list of entities.
     */
    List<DegreeLevelDTO> findAll();


    /**
     * Get the "id" degreeLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DegreeLevelDTO> findOne(Long id);

    /**
     * Delete the "id" degreeLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
