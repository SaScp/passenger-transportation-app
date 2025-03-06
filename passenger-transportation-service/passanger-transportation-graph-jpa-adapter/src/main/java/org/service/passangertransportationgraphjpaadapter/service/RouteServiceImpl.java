package org.service.passangertransportationgraphjpaadapter.service;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.Graph;
import org.service.passangertransportationgraphjpaadapter.dto.ParamsEntity;
import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;
import org.service.passangertransportationgraphjpaadapter.dto.RouteStepDto;
import org.service.passangertransportationgraphjpaadapter.filter_handler.HandlerExecutor;
import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;
import org.service.passangertransportationgraphjpaadapter.repository.RouteRepository;
import org.service.passangertransportationgraphjpaadapter.repository.RouteStepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final RouteStepRepository routeStepRepository;


    private final EntityManager entityManager;

    @Override
    public List<RouteDto> getByParams(ParamsEntity entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Route> findQuery = builder.createQuery(Route.class);
        Root<Route> rootObj = findQuery.from(Route.class);

        HandlerExecutor handlerExecutor = new HandlerExecutor(builder, rootObj);
        List<Predicate> predicateFilterList = handlerExecutor.execute(entity);

        findQuery.where(builder.and(predicateFilterList.toArray(new Predicate[0])));
        findQuery.orderBy(builder.asc(rootObj.get("departureTime")));

        List<Route> resultList = entityManager.createQuery(findQuery).getResultList();
        List<RouteDto> routeDtos = resultList.stream().map(RouteDto::fromRoute).toList();
        return routeDtos;
    }

    @Override
    public Graph getGraphByIds(List<String> ids) {
        List<RouteStep> routeSteps = routeStepRepository.findRouteStepsByRouteIdIn(ids);
        Graph graph = new Graph();
        graph.setNodes(createEdges(routeSteps));
        graph.setEdges(createNodes(routeSteps));
        return graph;
    }

    @Override
    public Graph getAll() {
        List<RouteStep> routeSteps = routeStepRepository.findAll();
        Graph graph = new Graph();
        graph.setNodes(createEdges(routeSteps));
        graph.setEdges(createNodes(routeSteps));

        return graph;
    }

    @Override
    public List<RouteDto> getByDepartureId(String id) {
        return routeRepository.findRoutesByDepartureCityId(id).stream().map(RouteDto::fromRoute).toList();
    }

    private Set<Map<String, String>> createNodes(List<RouteStep> routeSteps) {
        Set<Map<String, String>> result = new HashSet<>();

            int i = 0;
            int j = routeSteps.size() - 1;
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


        return result;
    }


    private Set<Map<String, String>> createEdges(List<RouteStep> routeSteps) {
        Set<Map<String, String>> result = new HashSet<>(routeSteps.size());
        int i = 0;
        int j = routeSteps.size() - 1;
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
