package org.service.output_port.util;

import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.model.RoutePageEntity;
import org.service.output_port.model.RouteStep;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RouteUtils {

    public  List<Route> getRoutes(List<RoutePageEntity> recursiveResults, Deque<String> idsQueue, Map<Long, Edge> longEdgeMap) {
        List<Route> routes = new ArrayList<>();

        for (var recursionElement : recursiveResults) {
            if (idsQueue.isEmpty()) {
                break;
            }
            Route route = new Route();
            route.setId(idsQueue.poll());
            int indexRouteStep = 1;
            if (recursionElement.path() != null) {
                for (var recursionPath : recursionElement.path()) {
                    RouteStep routeStep = new RouteStep(UUID.randomUUID(), indexRouteStep++, longEdgeMap.get(Long.parseLong(recursionPath)));
                    route.add(routeStep);
                }
            }
            route.setArrivalTime(recursionElement.getArrivTime());
            route.setDepartureTime(recursionElement.depTime());
            route.setDepartureCity(longEdgeMap.get(Long.parseLong(recursionElement.path()[0])).getFromLocationId());
            route.setArrivalCity(longEdgeMap.get(Long.parseLong(recursionElement.path()[recursionElement.path().length - 1])).getToLocationId());
            routes.add(route);
        }
        return routes;
    }
}
