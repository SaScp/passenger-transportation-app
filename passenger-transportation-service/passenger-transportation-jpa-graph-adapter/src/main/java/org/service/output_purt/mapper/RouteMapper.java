package org.service.output_purt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.service.entity.LocationEntity;
import org.service.entity.RouteStepEntity;
import org.service.entity.RoutesEntity;

import org.service.output_purt.model.Location;
import org.service.output_purt.model.Route;
import org.service.output_purt.model.RouteStep;
import org.service.output_purt.model.Type;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = "spring", imports = RouteStepMapper.class)
public interface RouteMapper {

    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    @Mapping(target = "departureTime", source = "departureTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "arrivalTime", source = "arrivalTime", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "type", source = "routeSteps", qualifiedByName = "mapType")
    @Mapping(target = "price", source = "routeSteps", qualifiedByName = "mapPrice")
    @Mapping(target = "departureCity.label", source = "departureCity.CName")
    @Mapping(target = "arrivalCity.label", source = "arrivalCity.CName")
    RoutesEntity routeToRoutesEntity(Route route);

    @Mapping(target = "edgeId.fromLocationId.label", source = "edgeId.fromLocationId.CName")
    @Mapping(target = "edgeId.toLocationId.label", source = "edgeId.toLocationId.CName")
    @Mapping(target = "edgeId.type", source = "edgeId.CType")
    RouteStepEntity routeStepToRouteStepEntity(RouteStep routeSteps);

    List<RouteStepEntity> routeStepsToRouteStepEntitys(List<RouteStep> routeSteps);

    List<RoutesEntity> routesToRouteEntitys(List<Route> routes);

    @Named("mapType")
    default String mapType(List<RouteStep> routeSteps) {
        Integer type = -1;
        for (var i : routeSteps) {
            if (type == -1) {
                type = i.getEdgeId().getCType();
            }
            if (!Objects.equals(i.getEdgeId().getCType(), type)) {
                return "null";
            }
        }
        return type.toString();
    }

    @Named("mapPrice")
    default Integer mapPrice(List<RouteStep> routeSteps) {
        int sum = 0;
        for (var i : routeSteps) {
            sum += i.getEdgeId().getPrice();
        }
        return sum;
    }

    // Метод для маппинга Location -> LocationEntity; MapStruct сам сгенерирует реализацию, если имена полей совпадают
    LocationEntity locationToLocationEntity(Location location);
}
