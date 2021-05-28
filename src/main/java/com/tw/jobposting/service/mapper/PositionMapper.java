package com.tw.jobposting.service.mapper;


import com.tw.jobposting.domain.*;
import com.tw.jobposting.service.dto.PositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Position} and its DTO {@link PositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PositionMapper extends EntityMapper<PositionDTO, Position> {



    Position toEntity(PositionDTO positionDTO);

    default Position fromId(Long id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}
