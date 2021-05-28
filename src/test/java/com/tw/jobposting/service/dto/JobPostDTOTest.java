package com.tw.jobposting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.jobposting.web.rest.TestUtil;

public class JobPostDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobPostDTO.class);
        JobPostDTO jobpostDTO1 = new JobPostDTO();
        jobpostDTO1.setId(1L);
        JobPostDTO jobpostDTO2 = new JobPostDTO();
        assertThat(jobpostDTO1).isNotEqualTo(jobpostDTO2);
        jobpostDTO2.setId(jobpostDTO1.getId());
        assertThat(jobpostDTO1).isEqualTo(jobpostDTO2);
        jobpostDTO2.setId(2L);
        assertThat(jobpostDTO1).isNotEqualTo(jobpostDTO2);
        jobpostDTO1.setId(null);
        assertThat(jobpostDTO1).isNotEqualTo(jobpostDTO2);
    }
}
