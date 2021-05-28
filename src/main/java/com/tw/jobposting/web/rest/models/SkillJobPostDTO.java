package com.tw.jobposting.web.rest.models;



import java.io.Serializable;

/**
 */
public class SkillJobPostDTO implements Serializable {

    private Long id;

    private Long skillId;

    private Long jobPostId;

    public SkillJobPostDTO() {
    }

    public SkillJobPostDTO(Long skillId, Long jobPostId) {
        this.skillId = skillId;
        this.jobPostId = jobPostId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillJobPostDTO)) {
            return false;
        }

        return id != null && id.equals(((SkillJobPostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillJobPostDTO{" +
            "id=" + getId() +
            ", skillId=" + getSkillId() +
            ", jobPostId=" + getJobPostId() +
            "}";
    }
}

