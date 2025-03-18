package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.model.Route;
import org.service.output_port.model.RoutePageEntity;
import org.service.output_port.repository.RouteRepository;
import org.service.output_port.util.BatchUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private final RouteRepository repository;

    private final BatchUtils batchUpdate;


    @Override
    @Transactional
    @Cacheable(key = "#entity.hashCode() % #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {

        List<String> routeIds;
        List<Route> routesByIdIn;
        Map<String, RoutePageEntity> routePageEntityMap = new HashMap<>();


        if (entity.getRouteId() == null || entity.getRouteId().isEmpty()) {
            List<RoutePageEntity> recursiveResults = repository.findRecursiveRoutes(entity.getFrom(), entity.getTo(), entity.getType(), entity.getTime().toString(), pageEntity.getPageSize(), pageEntity.getPageNum() * pageEntity.getPageSize());
            for (int i = 0; i < recursiveResults.size(); i++) {
                routePageEntityMap.put(UUID.nameUUIDFromBytes(recursiveResults.get(i).toString().getBytes()).toString(), recursiveResults.get(i));
            }

            routesByIdIn = repository.findRoutesByIdIn(routePageEntityMap.keySet());

            for (var i : routesByIdIn) {
                routePageEntityMap.remove(i.getId());
            }

            batchUpdate.executeSaveAll(routePageEntityMap, recursiveResults, routesByIdIn);
        } else {
            routeIds = entity.getRouteId();
            routesByIdIn = repository.findRoutesByIdIn(routeIds);
        }


        return RouteMapper.INSTANCE.routesToRouteEntitys(routesByIdIn);
    }


    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByParamsTransportationServiceOutputPort.class;
    }
}
