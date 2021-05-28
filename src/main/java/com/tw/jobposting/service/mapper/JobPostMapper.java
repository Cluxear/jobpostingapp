package com.tw.jobposting.service.mapper;


import com.tw.jobposting.domain.*;
import com.tw.jobposting.service.dto.JobPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobPost} and its DTO {@link JobPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {DegreeLevelMapper.class, PositionMapper.class})
public interface JobPostMapper extends EntityMapper<JobPostDTO, JobPost> {

    @Mapping(source = "degreeLevel.id", target = "degreeLevelId")
    @Mapping(source = "positon.id", target = "positonId")
    @Mapping(source="positon.jobTitle", target= "positionName")
    @Mapping(source="degreeLevel.name", target= "degreeLevelName")
    JobPostDTO toDto(JobPost jobpost);

    @Mapping(source = "degreeLevelId", target = "degreeLevel")
    @Mapping(source = "positonId", target = "positon")
    @Mapping(target = "requirements", ignore = true)
    @Mapping(target = "removeRequirements", ignore = true)
    JobPost toEntity(JobPostDTO jobpostDTO);

    default JobPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobPost jobpost = new JobPost();
        jobpost.setId(id);
        return jobpost;
    }
}
