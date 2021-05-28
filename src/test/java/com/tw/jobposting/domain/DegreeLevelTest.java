package com.tw.jobposting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.jobposting.web.rest.TestUtil;

public class DegreeLevelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegreeLevel.class);
        DegreeLevel degreeLevel1 = new DegreeLevel();
        degreeLevel1.setId(1L);
        DegreeLevel degreeLevel2 = new DegreeLevel();
        degreeLevel2.setId(degreeLevel1.getId());
        assertThat(degreeLevel1).isEqualTo(degreeLevel2);
        degreeLevel2.setId(2L);
        assertThat(degreeLevel1).isNotEqualTo(degreeLevel2);
        degreeLevel1.setId(null);
        assertThat(degreeLevel1).isNotEqualTo(degreeLevel2);
    }
}
