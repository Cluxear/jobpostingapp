package com.tw.jobposting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.jobposting.web.rest.TestUtil;

public class DegreeLevelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegreeLevelDTO.class);
        DegreeLevelDTO degreeLevelDTO1 = new DegreeLevelDTO();
        degreeLevelDTO1.setId(1L);
        DegreeLevelDTO degreeLevelDTO2 = new DegreeLevelDTO();
        assertThat(degreeLevelDTO1).isNotEqualTo(degreeLevelDTO2);
        degreeLevelDTO2.setId(degreeLevelDTO1.getId());
        assertThat(degreeLevelDTO1).isEqualTo(degreeLevelDTO2);
        degreeLevelDTO2.setId(2L);
        assertThat(degreeLevelDTO1).isNotEqualTo(degreeLevelDTO2);
        degreeLevelDTO1.setId(null);
        assertThat(degreeLevelDTO1).isNotEqualTo(degreeLevelDTO2);
    }
}
