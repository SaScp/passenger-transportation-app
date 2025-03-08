package org.service.output_purt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.service.output_port.entity.RouteStepEntity;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.RouteStep;
import org.service.output_purt.model.Type;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RouteStepMapper {

    RouteStepMapper INSTANCE = Mappers.getMapper(RouteStepMapper.class);

    List<RouteStepEntity> routeStepToRouteStepEntity(List<RouteStep> routeSteps);


    default String map(Location value) {
        return Optional.ofNullable(value).map(Location::getId).orElse("not found");
    }
}
