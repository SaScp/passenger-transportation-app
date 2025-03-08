package org.service.output_purt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.RoutesEntity;

import org.service.output_purt.model.Location;
import org.service.output_purt.model.Route;
import org.service.output_purt.model.Type;

import java.util.List;
import java.util.Optional;

@Mapper()
public interface RouteMapper {

    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);


    public List<RoutesEntity> routesToRouteEntitys(List<Route> routes);

    default String map(Type value){
        return Optional.ofNullable(value).map(Type::getTypeName).orElse("not found");
    }
}
