package org.service.output_port.factory;

import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.entity.RoutePageEntity;
import org.service.output_port.model.RouteStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RouteFactoryImpl implements RouteFactory {
    @Override
    public List<Route> createRoute(Map<String, RoutePageEntity> idsQueue, Map<Long, Edge> longEdgeMap) {
        List<Route> routes = new ArrayList<>();

        for (var i : idsQueue.keySet()) {
            Route route = new Route();
            route.setId(i);
            RoutePageEntity routePageEntity = idsQueue.get(i);
            int indexRouteStep = 1;
            if (routePageEntity.getEdgePath() != null) {
                for (var recursionPath : routePageEntity.getEdgePath()) {
                    RouteStep routeStep = new RouteStep(UUID.randomUUID(), indexRouteStep++, longEdgeMap.get(Long.parseLong(recursionPath)));
                    route.add(routeStep);
                }
            }
            route.setArrivalTime(routePageEntity.getArrTime());
            route.setDepartureTime(routePageEntity.getDepTime());
            route.setDepartureCity(longEdgeMap.get(Long.parseLong(routePageEntity.getEdgePath()[0])).getFromLocationId());
            route.setArrivalCity(longEdgeMap.get(Long.parseLong(routePageEntity.getEdgePath()[routePageEntity.getEdgePath().length - 1])).getToLocationId());
            routes.add(route);
        }

        return routes;
    }
}
