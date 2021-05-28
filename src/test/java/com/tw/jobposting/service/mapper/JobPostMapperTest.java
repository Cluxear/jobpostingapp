package com.tw.jobposting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobPostMapperTest {

    private JobPostMapper jobpostMapper;

    @BeforeEach
    public void setUp() {
        jobpostMapper = new JobPostMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobpostMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobpostMapper.fromId(null)).isNull();
    }
}
