package com.tw.jobposting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.jobposting.web.rest.TestUtil;

public class JobRequirementsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobRequirements.class);
        JobRequirements jobRequirements1 = new JobRequirements();
        jobRequirements1.setId(1L);
        JobRequirements jobRequirements2 = new JobRequirements();
        jobRequirements2.setId(jobRequirements1.getId());
        assertThat(jobRequirements1).isEqualTo(jobRequirements2);
        jobRequirements2.setId(2L);
        assertThat(jobRequirements1).isNotEqualTo(jobRequirements2);
        jobRequirements1.setId(null);
        assertThat(jobRequirements1).isNotEqualTo(jobRequirements2);
    }
}
