package org.service.core.factory.route_step;

import org.service.entity.RouteStepEntity;

import java.util.*;
import java.util.function.Function;

public class NodeFactory implements Function<List<RouteStepEntity>, Set<Map<String, String>>> {

    @Override
    public Set<Map<String, String>> apply(List<RouteStepEntity> routeStepEntities) {
        return createNodes(routeStepEntities);
    }


    private Set<Map<String, String>> createNodes(List<RouteStepEntity> routeSteps) {
        Set<Map<String, String>> result = new HashSet<>();

        int i = 0;
        int j = routeSteps.size() - 1;
        while (i <= j) {
            result.addAll(createNode(routeSteps, i));
            result.addAll(createNode(routeSteps, j));
            j--;
            i++;
        }


        return result;
    }

    private Set<Map<String, String>> createNode(List<RouteStepEntity> steps, int index) {
        return Set.of(
                Map.of("id",
                        Optional.ofNullable(steps.get(index).edgeId().fromLocationId().id()).orElse("UNKNOWN"),
                        "label",
                        Optional.ofNullable(steps.get(index).edgeId().fromLocationId().label()).orElse("UNKNOWN")
                ),
                Map.of("id",
                        Optional.ofNullable(steps.get(index).edgeId().toLocationId().id()).orElse("UNKNOWN"),
                        "label",
                        Optional.ofNullable(steps.get(index).edgeId().toLocationId().label()).orElse("UNKNOWN")
                )
        );
    }


}