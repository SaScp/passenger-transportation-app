package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
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
public class TransportationFindByDepartureCityAdapter implements FindAllRoutesByDepartureCityOutputPort {

    private final RouteRepository repository;

    private final RouteUtils routeUtils;

    @Override
    @Transactional
    @Cacheable(key = "#id + '_' + #pageEntity.hashCode()", value = "TransportationFindByDepartureCityAdapter::findAllByDepartureCity")
    public List<RoutesEntity> findAllByDepartureCity(String id, PageEntity pageEntity) {

        List<RoutePageEntity> recursiveResults = repository.findAllRecursiveRoutesById(
                id,
                pageEntity.pageSize(),
                pageEntity.pageNum() * pageEntity.pageSize(),
                6
        );

        return RouteMapper.INSTANCE.routesToRouteEntitys(routeUtils.getRoutesFromResult(recursiveResults));
    }



    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllRoutesByDepartureCityOutputPort.class;
    }
}
