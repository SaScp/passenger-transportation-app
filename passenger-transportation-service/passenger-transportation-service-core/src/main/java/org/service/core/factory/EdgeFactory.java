package org.service.core.factory;

import org.service.entity.LocationEntity;
import org.service.entity.EdgeEntity;
import org.service.entity.RouteStepEntity;

import java.util.*;
import java.util.function.Function;

public class EdgeFactory implements Function<List<RouteStepEntity>, Set<Map<String, String>>> {


    @Override
    public Set<Map<String, String>> apply(List<RouteStepEntity> routeStepEntities) {
        return createEdges(routeStepEntities);
    }

    private Set<Map<String, String>> createEdges(List<RouteStepEntity> routeSteps) {
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


    private Map<String, String> create(List<RouteStepEntity> steps, int index) {
        RouteStepEntity step = steps.get(index);
        return createEdge(
                step.routeStep().toString(),
                Optional.ofNullable(step.edgeId()).map(EdgeEntity::fromLocationId).map(LocationEntity::id).orElse("UNKNOWN"),
                Optional.ofNullable(step.edgeId()).map(EdgeEntity::toLocationId).map(LocationEntity::id).orElse("UNKNOWN"),
                step.routeId(),
                Optional.ofNullable(step.edgeId()).map(EdgeEntity::type).orElse(-1)
        );
    }


    private Map<String, String> createEdge(String id, String from, String to, String routeId, Integer type) {
        return Map.of("id", String.format("%s-%s", routeId, id), "from", from, "to", to, "route_id", routeId, "type", type.toString());
    }
}
