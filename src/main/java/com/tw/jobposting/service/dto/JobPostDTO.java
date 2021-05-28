package com.tw.jobposting.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tw.jobposting.domain.JobPost;
import com.tw.jobposting.domain.enumeration.LocationType;
import com.tw.jobposting.domain.enumeration.EmploymentType;

/**
 * A DTO for the {@link JobPost} entity.
 */
public class JobPostDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Double estimatedSalary;

    private LocationType type;

    private EmploymentType employmentType;

    private Instant createdAt;

    private Instant modifiedAt;


    private Long degreeLevelId;

    private Long positonId;

    private String degreeLevelName;

    private String positionName;

    private List<Long> skillId = new ArrayList<>();

    public List<Long> getSkillId() {
        return skillId;
    }

    public void setSkillId(List<Long> skillId) {
        this.skillId = skillId;
    }
    private List<Skill> skills = new ArrayList<>();


   public void addSkill(Skill skill) {
        skills.add(skill);
    }
    public String getDegreeLevelName() {
        return degreeLevelName;
    }

    public void setDegreeLevelName(String degreeLevelName) {
        this.degreeLevelName = degreeLevelName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEstimatedSalary() {
        return estimatedSalary;
    }

    public void setEstimatedSalary(Double estimatedSalary) {
        this.estimatedSalary = estimatedSalary;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getDegreeLevelId() {
        return degreeLevelId;
    }

    public void setDegreeLevelId(Long degreeLevelId) {
        this.degreeLevelId = degreeLevelId;
    }

    public Long getPositonId() {
        return positonId;
    }

    public void setPositonId(Long positionId) {
        this.positonId = positionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobPostDTO)) {
            return false;
        }

        return id != null && id.equals(((JobPostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobpostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", estimatedSalary=" + getEstimatedSalary() +
            ", type='" + getType() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", degreeLevelId=" + getDegreeLevelId() +
            ", positonId=" + getPositonId() +
            ", skillIds=" + getSkillId() +
            "}";
    }
}
