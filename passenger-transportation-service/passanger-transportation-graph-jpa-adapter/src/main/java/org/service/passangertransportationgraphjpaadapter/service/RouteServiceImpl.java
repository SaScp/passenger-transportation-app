package org.service.passangertransportationgraphjpaadapter.service;

import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.Graph;
import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;
import org.service.passangertransportationgraphjpaadapter.dto.RouteStepDto;
import org.service.passangertransportationgraphjpaadapter.model.Location;
import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;
import org.service.passangertransportationgraphjpaadapter.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;


    @Override
    public Map<String, ?> getByDepartureId(String id) {

        Map<String, Object> result = new LinkedHashMap<>();
        List<Route> routes = routeRepository.findRoutesByDepartureCityId(id);
        List<RouteDto> routeDtos = routes.stream().map(RouteDto::fromRoute).toList();
        Graph graph = new Graph();
        graph.setEdges(createEdges(routes));
        graph.setNodes(createNodes(routes));
        result.put("routes",routeDtos);
        result.put("graph",graph);
        return result;
    }
    private List<Map<String, String>> createNodes(List<Route> routes) {
        int resultSize = 0;
        for (var i : routes) {
            resultSize += i.getRouteSteps().size();
        }
        List<Map<String, String>> result = new ArrayList<>(resultSize);

        for (var route : routes) {
            int i = 0;
            int j = route.getRouteSteps().size() - 1;
            List<RouteStep> routeSteps = route.getRouteSteps();
            while (i <= j) {
                if (i == j) {
                    result.add(createNode(routeSteps, i));
                    result.add(createNode2(routeSteps, i));
                } else {
                    result.add(createNode(routeSteps, i));
                    result.add(createNode2(routeSteps, i));
                    result.add(createNode(routeSteps, j));
                    result.add(createNode2(routeSteps, j));
                    j--;
                }
                i++;
            }
        }


        return result;
    }


    private  List<Map<String, String>> createEdges(List<Route> routes) {
        int resultSize = 0;
        for (var i : routes) {
            resultSize += i.getRouteSteps().size();
        }
        List<Map<String, String>> result = new ArrayList<>(resultSize);

        for (var route : routes) {
            int i = 0;
            int j = route.getRouteSteps().size() - 1;
            List<RouteStep> routeSteps = route.getRouteSteps();
            while (i <= j) {
                if (i == j) {
                    result.add(create(routeSteps, i));
                } else {
                    result.add(create(routeSteps, i));
                    result.add(create(routeSteps, j));
                    j--;
                }
                i++;
            }
        }

        return result;
    }
    private Map<String, String> createNode(List<RouteStep> steps, int index) {
        return Map.of("id", steps.get(index).getEdgeId().getFromLocationId().getId(), "label", steps.get(index).getEdgeId().getFromLocationId().getCName());
    }

    private Map<String, String> createNode2(List<RouteStep> steps, int index) {
        return Map.of("id", steps.get(index).getEdgeId().getToLocationId().getId(), "label", steps.get(index).getEdgeId().getToLocationId().getCName());
    }
    private Map<String, String> create(List<RouteStep> steps, int index) {
        return createEdge(steps.get(index).getRouteStep().toString(), steps.get(index).getEdgeId().getFromLocationId().getId(), steps.get(index).getEdgeId().getToLocationId().getId(), steps.get(index).getRouteId());
    }

    private Map<String, String> createEdge(String id, String from, String to, String routeId) {
        return Map.of("id", String.format("%s-%s", routeId, id), "from", from, "to", to, "route_id", routeId);
    }
}
