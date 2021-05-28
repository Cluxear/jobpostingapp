package com.tw.jobposting.web.rest;

import com.tw.jobposting.service.DegreeLevelService;
import com.tw.jobposting.web.rest.errors.BadRequestAlertException;
import com.tw.jobposting.service.dto.DegreeLevelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.jobposting.domain.DegreeLevel}.
 */
@RestController
@RequestMapping("/api")
public class DegreeLevelResource {

    private final Logger log = LoggerFactory.getLogger(DegreeLevelResource.class);

    private static final String ENTITY_NAME = "jobpostingDegreeLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DegreeLevelService degreeLevelService;

    public DegreeLevelResource(DegreeLevelService degreeLevelService) {
        this.degreeLevelService = degreeLevelService;
    }

    /**
     * {@code POST  /degree-levels} : Create a new degreeLevel.
     *
     * @param degreeLevelDTO the degreeLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new degreeLevelDTO, or with status {@code 400 (Bad Request)} if the degreeLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/degree-levels")
    public ResponseEntity<DegreeLevelDTO> createDegreeLevel(@RequestBody DegreeLevelDTO degreeLevelDTO) throws URISyntaxException {
        log.debug("REST request to save DegreeLevel : {}", degreeLevelDTO);
        if (degreeLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new degreeLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DegreeLevelDTO result = degreeLevelService.save(degreeLevelDTO);
        return ResponseEntity.created(new URI("/api/degree-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /degree-levels} : Updates an existing degreeLevel.
     *
     * @param degreeLevelDTO the degreeLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated degreeLevelDTO,
     * or with status {@code 400 (Bad Request)} if the degreeLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the degreeLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/degree-levels")
    public ResponseEntity<DegreeLevelDTO> updateDegreeLevel(@RequestBody DegreeLevelDTO degreeLevelDTO) throws URISyntaxException {
        log.debug("REST request to update DegreeLevel : {}", degreeLevelDTO);
        if (degreeLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DegreeLevelDTO result = degreeLevelService.save(degreeLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, degreeLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /degree-levels} : get all the degreeLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of degreeLevels in body.
     */
    @GetMapping("/degree-levels")
    public List<DegreeLevelDTO> getAllDegreeLevels() {
        log.debug("REST request to get all DegreeLevels");
        return degreeLevelService.findAll();
    }

    /**
     * {@code GET  /degree-levels/:id} : get the "id" degreeLevel.
     *
     * @param id the id of the degreeLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the degreeLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/degree-levels/{id}")
    public ResponseEntity<DegreeLevelDTO> getDegreeLevel(@PathVariable Long id) {
        log.debug("REST request to get DegreeLevel : {}", id);
        Optional<DegreeLevelDTO> degreeLevelDTO = degreeLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(degreeLevelDTO);
    }

    /**
     * {@code DELETE  /degree-levels/:id} : delete the "id" degreeLevel.
     *
     * @param id the id of the degreeLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/degree-levels/{id}")
    public ResponseEntity<Void> deleteDegreeLevel(@PathVariable Long id) {
        log.debug("REST request to delete DegreeLevel : {}", id);
        degreeLevelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
