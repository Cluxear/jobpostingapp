package com.tw.jobposting.service.impl;

import com.tw.jobposting.service.JobPostService;
import com.tw.jobposting.domain.JobPost;
import com.tw.jobposting.repository.JobPostRepository;
import com.tw.jobposting.service.dto.JobPostDTO;
import com.tw.jobposting.service.mapper.JobPostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link JobPost}.
 */
@Service
@Transactional
public class JobPostServiceImpl implements JobPostService {

    private final Logger log = LoggerFactory.getLogger(JobPostServiceImpl.class);

    private final JobPostRepository jobpostRepository;

    private final JobPostMapper jobpostMapper;

    public JobPostServiceImpl(JobPostRepository jobpostRepository, JobPostMapper jobpostMapper) {
        this.jobpostRepository = jobpostRepository;
        this.jobpostMapper = jobpostMapper;
    }

    @Override
    public JobPostDTO save(JobPostDTO jobpostDTO) {
        log.debug("Request to save Jobpost : {}", jobpostDTO);
        JobPost jobpost = jobpostMapper.toEntity(jobpostDTO);
        jobpost = jobpostRepository.save(jobpost);
        return jobpostMapper.toDto(jobpost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostDTO> findAll() {
        log.debug("Request to get all Jobposts");
        return jobpostRepository.findAll().stream()
            .map(jobpostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<JobPostDTO> findOne(Long id) {
        log.debug("Request to get Jobpost : {}", id);
        return jobpostRepository.findById(id)
            .map(jobpostMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jobpost : {}", id);
        jobpostRepository.deleteById(id);
    }
}
