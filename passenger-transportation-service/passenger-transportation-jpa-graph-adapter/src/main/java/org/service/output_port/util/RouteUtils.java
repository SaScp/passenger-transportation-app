package org.service.output_port.util;

import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.model.RouteEntityTemp;
import org.service.output_port.model.RouteStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;


@Component
public class RouteUtils {

    public  List<Route> getRoutes(List<RouteEntityTemp> recursiveResults, Deque<String> idsQueue, Map<Long, Edge> idsEl) {
        List<Route> routes = new ArrayList<>();

        for (var rec : recursiveResults) {
            if (idsQueue.isEmpty()) {
                break;
            }
            Route route = new Route();
            route.setId(idsQueue.poll());
            int i = 1;
            for (var j : rec.path()) {
                RouteStep routeStep = new RouteStep(i++, idsEl.get(Long.parseLong(j)));
                route.add(routeStep);
            }
            route.setArrivalTime(rec.getArrivTime());
            route.setDepartureTime(rec.depTime());
            route.setDepartureCity(idsEl.get(Long.parseLong(rec.path()[0])).getFromLocationId());
            route.setArrivalCity(idsEl.get(Long.parseLong(rec.path()[rec.path().length - 1])).getToLocationId());
            routes.add(route);
        }
        return routes;
    }
}
