package com.tw.jobposting.service.dto;

import java.io.Serializable;

/**
 */
public class UserApplicationDTO implements Serializable {

    private Long id;

    private String userId;

    private Long applicationId;

    private Long jobPostId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
        if (!(o instanceof UserApplicationDTO)) {
            return false;
        }

        return id != null && id.equals(((UserApplicationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApplicationDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", applicationId=" + getApplicationId() +
            ", jobPostId=" + getJobPostId() +
            "}";
    }
}

