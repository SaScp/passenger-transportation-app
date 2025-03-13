package org.service.core.factory.edge;

import org.service.entity.EdgeEntity;

import java.util.*;
import java.util.function.Function;

public class NodeFactory implements Function<List<EdgeEntity>, Set<Map<String, String>>> {

    @Override
    public Set<Map<String, String>> apply(List<EdgeEntity> routeStepEntities) {
        return createNodes(routeStepEntities);
    }


    private Set<Map<String, String>> createNodes(List<EdgeEntity> routeSteps) {
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


    private Set<Map<String, String>> createNode(List<EdgeEntity> steps, int index) {
        return Set.of(
                Map.of("id",
                        Optional.ofNullable(steps.get(index).fromLocationId().id()).orElse("UNKNOWN"),
                        "label",
                        Optional.ofNullable(steps.get(index).fromLocationId().label()).orElse("UNKNOWN")
                ),
                Map.of("id",
                        Optional.ofNullable(steps.get(index).toLocationId().id()).orElse("UNKNOWN"),
                        "label",
                        Optional.ofNullable(steps.get(index).toLocationId().label()).orElse("UNKNOWN")
                )
        );
    }


}
