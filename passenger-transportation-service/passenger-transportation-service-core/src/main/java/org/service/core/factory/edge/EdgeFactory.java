package org.service.core.factory.edge;

import org.service.entity.EdgeEntity;

import java.util.*;
import java.util.function.Function;

public class EdgeFactory implements Function<List<EdgeEntity>, Set<Map<String, String>>> {


    @Override
    public Set<Map<String, String>> apply(List<EdgeEntity> routeStepEntities) {
        return createEdges(routeStepEntities);
    }

    private Set<Map<String, String>> createEdges(List<EdgeEntity> routeSteps) {
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


    private Map<String, String> create(List<EdgeEntity> steps, int index) {
        EdgeEntity step = steps.get(index);
        return createEdge(
                step.id(),
                Optional.ofNullable(step.fromLocationId().id()).orElse("UNKNOWN"),
                Optional.ofNullable(step.toLocationId().id()).orElse("UNKNOWN"),
                Optional.ofNullable(step.type()).orElse("UNKNOWN")
        );
    }


    private Map<String, String> createEdge(Long id, String from, String to, String type) {
        return Map.of("id",  String.valueOf(id), "from", from, "to", to , "type", type);
    }
}
