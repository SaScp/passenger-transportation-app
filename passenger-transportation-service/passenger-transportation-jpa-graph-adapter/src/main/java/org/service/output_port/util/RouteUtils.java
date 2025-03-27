package org.service.output_port.util;

import lombok.AllArgsConstructor;
import org.service.output_port.model.Route;
import org.service.output_port.entity.RoutePageEntity;
import org.service.output_port.repository.RouteRepository;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@AllArgsConstructor
public class RouteUtils {

    private final RouteRepository repository;

    private final BatchUtils batchUtils;

    public List<Route> getRoutesFromResult(List<RoutePageEntity> recursiveResults) {

        Map<String, RoutePageEntity> routePageEntityMap = new HashMap<>();
        List<Route> routesByIdIn;

        recursiveResults.forEach((routePageEntity -> {
                    routePageEntityMap.put(UUID.nameUUIDFromBytes(routePageEntity.toString().getBytes()).toString(), routePageEntity);
                })
        );
        routesByIdIn = repository.findRoutesByIdIn(routePageEntityMap.keySet());

        for (var i : routesByIdIn) {
            routePageEntityMap.remove(i.getId());
        }
        batchUtils.executeSaveAll(routePageEntityMap, recursiveResults, routesByIdIn);
        return routesByIdIn;
    }


}
