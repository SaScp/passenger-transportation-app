package org.service.ouptput_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.model.TransportType;

import java.util.List;
import java.util.Optional;

@Mapper()
public interface RouteMapper {

    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    public List<RoutesEntity> routesToRouteEntitys(List<Route> routes);

    default String map(TransportType value){
        return Optional.ofNullable(value).map(TransportType::getTransportType).orElse("not found");
    }
}
