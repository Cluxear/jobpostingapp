package com.tw.jobposting.repository;

import com.tw.jobposting.domain.JobRequirements;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobRequirements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRequirementsRepository extends JpaRepository<JobRequirements, Long> {
}
