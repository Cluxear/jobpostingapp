package com.tw.jobposting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.jobposting.web.rest.TestUtil;

public class JobPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobPost.class);
        JobPost jobpost1 = new JobPost();
        jobpost1.setId(1L);
        JobPost jobpost2 = new JobPost();
        jobpost2.setId(jobpost1.getId());
        assertThat(jobpost1).isEqualTo(jobpost2);
        jobpost2.setId(2L);
        assertThat(jobpost1).isNotEqualTo(jobpost2);
        jobpost1.setId(null);
        assertThat(jobpost1).isNotEqualTo(jobpost2);
    }
}
