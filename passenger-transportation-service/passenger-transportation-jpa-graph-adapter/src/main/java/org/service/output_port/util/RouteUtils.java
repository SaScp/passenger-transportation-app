package org.service.output_port.util;

import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.model.RoutePageEntity;
import org.service.output_port.model.RouteStep;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RouteUtils {

    public  List<Route> getRoutes(List<RoutePageEntity> recursiveResults, Map<String, RoutePageEntity> idsQueue, Map<Long, Edge> longEdgeMap) {
        List<Route> routes = new ArrayList<>();

        for (var i : idsQueue.keySet()) {
            if (idsQueue.isEmpty()) {
                break;
            }
            Route route = new Route();
            route.setId(i);
            RoutePageEntity routePageEntity = idsQueue.get(i);
            int indexRouteStep = 1;
            if (routePageEntity.path() != null) {
                for (var recursionPath : routePageEntity.path()) {
                    RouteStep routeStep = new RouteStep(UUID.randomUUID(), indexRouteStep++, longEdgeMap.get(Long.parseLong(recursionPath)));
                    route.add(routeStep);
                }
            }
            route.setArrivalTime(routePageEntity.getArrivTime());
            route.setDepartureTime(routePageEntity.depTime());
            route.setDepartureCity(longEdgeMap.get(Long.parseLong(routePageEntity.path()[0])).getFromLocationId());
            route.setArrivalCity(longEdgeMap.get(Long.parseLong(routePageEntity.path()[routePageEntity.path().length - 1])).getToLocationId());
            routes.add(route);
        }

        return routes;
    }
}
