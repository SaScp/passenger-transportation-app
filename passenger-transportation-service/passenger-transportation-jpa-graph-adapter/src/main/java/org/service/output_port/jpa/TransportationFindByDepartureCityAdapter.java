package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
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
@Transactional
public class TransportationFindByDepartureCityAdapter implements FindAllRoutesByDepartureCityOutputPort {

    private final RouteRepository repository;

    private final BatchUtils batchUpdate;

    @Override
    @Cacheable(key = "#id + '_' + #pageEntity.hashCode()", value = "TransportationFindByDepartureCityAdapter::findAllByDepartureCity")
    public List<RoutesEntity> findAllByDepartureCity(String id, PageEntity pageEntity) {

        List<RoutePageEntity> recursiveResults = repository.findAllRecursiveRoutesById(id, pageEntity.getPageSize(), pageEntity.getPageNum() * pageEntity.getPageSize());
        Deque<String> idsQueue = new LinkedList<>();
        for (int i = 0; i < recursiveResults.size(); i++) {
            idsQueue.push(UUID.nameUUIDFromBytes(recursiveResults.get(i).toString().getBytes()).toString());
        }

        List<Route> routesByIdIn = repository.findRoutesByIdIn(idsQueue);
        for (var i : routesByIdIn) {
            idsQueue.remove(i.getId());
        }

        batchUpdate.executeSaveAll(idsQueue, recursiveResults, routesByIdIn);


        return RouteMapper.INSTANCE.routesToRouteEntitys(routesByIdIn);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllRoutesByDepartureCityOutputPort.class;
    }
}
