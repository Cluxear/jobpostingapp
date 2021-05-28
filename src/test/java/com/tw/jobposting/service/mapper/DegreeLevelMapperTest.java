package com.tw.jobposting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DegreeLevelMapperTest {

    private DegreeLevelMapper degreeLevelMapper;

    @BeforeEach
    public void setUp() {
        degreeLevelMapper = new DegreeLevelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(degreeLevelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(degreeLevelMapper.fromId(null)).isNull();
    }
}
