package org.service.output_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.service.entity.LocationEntity;
import org.service.entity.RouteStepEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.model.Location;
import org.service.output_port.model.Route;
import org.service.output_port.model.RouteStep;

import java.util.List;
import java.util.Objects;

@Mapper
public interface RouteMapper {

    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    @Mapping(target = "departureTime", source = "departureTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "arrivalTime", source = "arrivalTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "type", source = "routeSteps", qualifiedByName = "mapType")
    @Mapping(target = "price", source = "routeSteps", qualifiedByName = "mapPrice")
    @Mapping(target = "departureCity.label", source = "departureCity.CName")
    @Mapping(target = "arrivalCity.label", source = "arrivalCity.CName")

    RoutesEntity routeToRoutesEntity(Route route);

    @Mapping(target = "edgeId.fromLocationId.label", source = "edgeId.fromLocationId.CName")
    @Mapping(target = "edgeId.toLocationId.label", source = "edgeId.toLocationId.CName")
    @Mapping(target = "edgeId.type", source = "edgeId.type.typeName")
    @Mapping(target = "routeId", source = "route.id")
    RouteStepEntity routeStepToRouteStepEntity(RouteStep routeSteps);

    List<RouteStepEntity> routeStepsToRouteStepEntitys(List<RouteStep> routeSteps);

    List<RoutesEntity> routesToRouteEntitys(List<Route> routes);

    @Named("mapType")
    default String mapType(List<RouteStep> routeSteps) {
        String type = "";
        for (var i : routeSteps) {
            if (type.isEmpty()) {
                type = i.getEdgeId().getType().getTypeName();
            }
            if (!Objects.equals(i.getEdgeId().getType().getTypeName(), type)) {
                return "Микс";
            }
        }
        return type;
    }

    @Named("mapPrice")
    default Integer mapPrice(List<RouteStep> routeSteps) {
        int sum = 0;
        for (var i : routeSteps) {
            sum += i.getEdgeId().getPrice();
        }
        return sum;
    }

    LocationEntity locationToLocationEntity(Location location);
}
