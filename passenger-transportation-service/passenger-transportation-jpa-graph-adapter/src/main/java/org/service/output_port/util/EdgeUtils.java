package org.service.output_port.util;

import org.service.output_port.model.Edge;
import org.service.output_port.entity.RoutePageEntity;
import org.service.output_port.repository.EdgeRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EdgeUtils {

    private final EdgeRepository edgeRepository;

    public EdgeUtils(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }


    public Map<Integer, Edge> getLongEdgeMap(List<RoutePageEntity> recursiveResults) {
        List<Integer> ids = new ArrayList<>();
        recursiveResults.forEach(row -> {
            Collections.addAll(ids, row.getEdgePath());
        });
        var a = edgeRepository.findAllByIdIn(ids);
        Map<Integer, Edge> longEdgeMap = new HashMap<>();

        for (var j : a) {
            if (!longEdgeMap.containsKey(j.getId().intValue())) {
                longEdgeMap.put(Math.toIntExact(j.getId()), j);
            }
        }
        return longEdgeMap;
    }
}
