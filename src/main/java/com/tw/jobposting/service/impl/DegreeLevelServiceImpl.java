package com.tw.jobposting.service.impl;

import com.tw.jobposting.service.DegreeLevelService;
import com.tw.jobposting.domain.DegreeLevel;
import com.tw.jobposting.repository.DegreeLevelRepository;
import com.tw.jobposting.service.dto.DegreeLevelDTO;
import com.tw.jobposting.service.mapper.DegreeLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DegreeLevel}.
 */
@Service
@Transactional
public class DegreeLevelServiceImpl implements DegreeLevelService {

    private final Logger log = LoggerFactory.getLogger(DegreeLevelServiceImpl.class);

    private final DegreeLevelRepository degreeLevelRepository;

    private final DegreeLevelMapper degreeLevelMapper;

    public DegreeLevelServiceImpl(DegreeLevelRepository degreeLevelRepository, DegreeLevelMapper degreeLevelMapper) {
        this.degreeLevelRepository = degreeLevelRepository;
        this.degreeLevelMapper = degreeLevelMapper;
    }

    @Override
    public DegreeLevelDTO save(DegreeLevelDTO degreeLevelDTO) {
        log.debug("Request to save DegreeLevel : {}", degreeLevelDTO);
        DegreeLevel degreeLevel = degreeLevelMapper.toEntity(degreeLevelDTO);
        degreeLevel = degreeLevelRepository.save(degreeLevel);
        return degreeLevelMapper.toDto(degreeLevel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DegreeLevelDTO> findAll() {
        log.debug("Request to get all DegreeLevels");
        return degreeLevelRepository.findAll().stream()
            .map(degreeLevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DegreeLevelDTO> findOne(Long id) {
        log.debug("Request to get DegreeLevel : {}", id);
        return degreeLevelRepository.findById(id)
            .map(degreeLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DegreeLevel : {}", id);
        degreeLevelRepository.deleteById(id);
    }
}
