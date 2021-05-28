package com.tw.jobposting.service.mapper;


import com.tw.jobposting.domain.*;
import com.tw.jobposting.service.dto.DegreeLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DegreeLevel} and its DTO {@link DegreeLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DegreeLevelMapper extends EntityMapper<DegreeLevelDTO, DegreeLevel> {



    default DegreeLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DegreeLevel degreeLevel = new DegreeLevel();
        degreeLevel.setId(id);
        return degreeLevel;
    }
}
