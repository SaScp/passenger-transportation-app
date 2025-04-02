package org.service.input_port;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.entity.TypeEntity;

import java.util.List;

public interface RouteTransportationServiceInputPort {

    List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity);

    List<RoutesEntity> findAll(PageEntity pageEntity);

    List<TypeEntity> findAllType();

    List<RoutesEntity> findRoutesByDepId(String id, PageEntity pageEntity);
}
