package org.service.output_purt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.service.entity.LocationEntity;
import org.service.output_port.entity.EdgeEntity;
import org.service.output_port.entity.RouteStepEntity;
import org.service.output_purt.model.Edge;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.RouteStep;
import org.service.output_purt.model.Type;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RouteStepMapper {

    RouteStepMapper INSTANCE = Mappers.getMapper(RouteStepMapper.class);



    List<RouteStepEntity> routeStepToRouteStepEntity(List<RouteStep> routeSteps);

    default EdgeEntity edgeToEdgeEntity(Edge edge) {
        if (edge == null) {
            return null;
        } else {
            return new EdgeEntity(
                    createLocationEntity( edge.getFromLocationId().getId(), edge.getFromLocationId().getCName()),
                    createLocationEntity( edge.getToLocationId().getId(), edge.getToLocationId().getCName()),
                    edge.getCType());
        }
    }
    default LocationEntity createLocationEntity(String id, String loc) {
        return new LocationEntity(id, loc);
    }


    default String map(Location value) {
        return Optional.ofNullable(value).map(Location::getId).orElse("not found");
    }
}
