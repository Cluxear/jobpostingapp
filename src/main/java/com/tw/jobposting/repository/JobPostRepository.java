package com.tw.jobposting.repository;

import com.tw.jobposting.domain.JobPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Jobpost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
