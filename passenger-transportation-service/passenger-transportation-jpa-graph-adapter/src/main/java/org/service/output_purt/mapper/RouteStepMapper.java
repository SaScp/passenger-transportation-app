package org.service.output_purt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.LocationEntity;
import org.service.output_port.entity.EdgeEntity;
import org.service.output_port.entity.RouteStepEntity;
import org.service.output_purt.model.Edge;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.RouteStep;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RouteStepMapper {

    RouteStepMapper INSTANCE = Mappers.getMapper(RouteStepMapper.class);

    @Mapping(target = "edgeId.fromLocationId.label", source = "edgeId.fromLocationId.CName")
    @Mapping(target = "edgeId.toLocationId.label", source = "edgeId.toLocationId.CName")
    @Mapping(target = "edgeId.type", source = "edgeId.CType")
    RouteStepEntity routeStepToRouteStepEntity(RouteStep routeSteps);

    List<RouteStepEntity> routeStepsToRouteStepEntitys(List<RouteStep> routeSteps);

    default LocationEntity createLocationEntity(String id, String loc) {
        return new LocationEntity(id, loc);
    }


    default String map(Location value) {
        return Optional.ofNullable(value).map(Location::getId).orElse("not found");
    }
}
