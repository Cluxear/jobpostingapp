package com.tw.jobposting.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.tw.jobposting.domain.enumeration.LocationType;

import com.tw.jobposting.domain.enumeration.EmploymentType;

/**
 * A Jobpost.
 */
@Entity
@Table(name = "job_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "estimated_salary")
    private Double estimatedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LocationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @ManyToOne
    private DegreeLevel degreeLevel;

    @ManyToOne
    private Position positon;

    @OneToMany(mappedBy = "jobpost")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<JobRequirements> requirements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public JobPost title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public JobPost description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEstimatedSalary() {
        return estimatedSalary;
    }

    public JobPost estimatedSalary(Double estimatedSalary) {
        this.estimatedSalary = estimatedSalary;
        return this;
    }

    public void setEstimatedSalary(Double estimatedSalary) {
        this.estimatedSalary = estimatedSalary;
    }

    public LocationType getType() {
        return type;
    }

    public JobPost type(LocationType type) {
        this.type = type;
        return this;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public JobPost employmentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
        return this;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public JobPost createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public JobPost modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    public JobPost degreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
        return this;
    }

    public void setDegreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public Position getPositon() {
        return positon;
    }

    public JobPost positon(Position position) {
        this.positon = position;
        return this;
    }

    public void setPositon(Position position) {
        this.positon = position;
    }

    public Set<JobRequirements> getRequirements() {
        return requirements;
    }

    public JobPost requirements(Set<JobRequirements> jobRequirements) {
        this.requirements = jobRequirements;
        return this;
    }

    public JobPost addRequirements(JobRequirements jobRequirement) {
        this.requirements.add(jobRequirement);
        jobRequirement.setJobpost(this);
        return this;
    }

    public JobPost removeRequirements(JobRequirements jobRequirement) {
        this.requirements.remove(jobRequirement);
        jobRequirement.setJobpost(null);
        return this;
    }

    public void setRequirements(Set<JobRequirements> jobRequirements) {
        this.requirements = jobRequirements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobPost)) {
            return false;
        }
        return id != null && id.equals(((JobPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jobpost{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", estimatedSalary=" + getEstimatedSalary() +
            ", type='" + getType() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
