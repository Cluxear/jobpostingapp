package com.tw.jobposting.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A JobRequirements.
 */
@Entity
@Table(name = "job_requirements")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobRequirements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobRequirements", allowSetters = true)
    private JobPost jobpost;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public JobRequirements content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JobPost getJobpost() {
        return jobpost;
    }

    public JobRequirements jobpost(JobPost jobpost) {
        this.jobpost = jobpost;
        return this;
    }

    public void setJobpost(JobPost jobpost) {
        this.jobpost = jobpost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobRequirements)) {
            return false;
        }
        return id != null && id.equals(((JobRequirements) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobRequirements{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
