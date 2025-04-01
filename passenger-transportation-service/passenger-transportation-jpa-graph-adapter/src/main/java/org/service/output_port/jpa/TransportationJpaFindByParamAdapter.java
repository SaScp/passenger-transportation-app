package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.entity.RoutePageEntity;
import org.service.output_port.repository.RouteRepository;
import org.service.output_port.util.RouteUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private final RouteRepository repository;

    private final RouteUtils routeUtils;


    @Override
    @Transactional
    @Cacheable(key = "#entity.hashCode() - #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {

        if (entity.routeId() == null || entity.routeId().isEmpty()) {

            List<RoutePageEntity> recursiveResults = repository.findRecursiveRoutes(
                    entity.from() + "%",
                    entity.to() + "%",
                    entity.type(),
                    entity.time().toString(),
                    pageEntity.pageSize(),
                    pageEntity.pageNum() * pageEntity.pageSize(), 5);

            return RouteMapper.INSTANCE.routesToRouteEntitys(routeUtils.getRoutesFromResult(recursiveResults));
        } else {

            List<String> routeIds = entity.routeId();
            return RouteMapper.INSTANCE.routesToRouteEntitys(repository.findRoutesByIdIn(routeIds));
        }

    }


    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByParamsTransportationServiceOutputPort.class;
    }
}
