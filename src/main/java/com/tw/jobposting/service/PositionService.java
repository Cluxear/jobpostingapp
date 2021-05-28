package com.tw.jobposting.service;

import com.tw.jobposting.service.dto.PositionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.jobposting.domain.Position}.
 */
public interface PositionService {

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    PositionDTO save(PositionDTO positionDTO);

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<PositionDTO> findAll();



    /**
     * Get the "id" position.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PositionDTO> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
