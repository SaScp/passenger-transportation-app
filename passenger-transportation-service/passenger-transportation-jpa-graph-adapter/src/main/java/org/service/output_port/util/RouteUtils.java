package org.service.output_port.util;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import lombok.AllArgsConstructor;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.factory.RouteFactoryImpl;
import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.entity.RoutePageEntity;
import org.service.output_port.repository.RouteRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Component
@AllArgsConstructor
public class RouteUtils {

    private final RouteRepository repository;

    private final BatchUtils batchUtils;

    private EdgeUtils edgeUtils;

    private final RouteFactory routeFactory = new RouteFactoryImpl();

    public List<Route> getRoutesFromResult(List<RoutePageEntity> recursiveResults) {
        Map<String, RoutePageEntity> routePageEntityMap = new HashMap<>();
        recursiveResults.forEach((routePageEntity -> {
                    routePageEntityMap.put(UUID.nameUUIDFromBytes(routePageEntity.toString().getBytes()).toString(), routePageEntity);
                })
        );
        Map<Integer, Edge> longEdgeMap = edgeUtils.getLongEdgeMap(recursiveResults);
        List<Route> routes = routeFactory.createRoute(routePageEntityMap, longEdgeMap);

        execBatch(routePageEntityMap);
        return routes;
    }

    private void execBatch(Map<String, RoutePageEntity> routePageEntityMap) {
        List<Route> routesByIdIn = repository.findRoutesByIdIn(routePageEntityMap.keySet());

        for (var i : routesByIdIn) {
            routePageEntityMap.remove(i.getId());
        }
        batchUtils.executeSaveAll(routePageEntityMap, routesByIdIn);
    }


}
