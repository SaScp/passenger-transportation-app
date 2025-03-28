package org.service.output_port.factory;

import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.entity.RoutePageEntity;

import java.util.List;
import java.util.Map;

public interface RouteFactory {

    List<Route> createRoute(Map<String, RoutePageEntity> idsQueue, Map<Integer, Edge> longEdgeMap);
}
