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


    public Map<Long, Edge> getLongEdgeMap(List<RoutePageEntity> recursiveResults) {
        List<String> ids = new ArrayList<>();
        recursiveResults.forEach(row -> {
            Collections.addAll(ids, row.getEdgePath());
        });
        var a = edgeRepository.findAllByIdIn(ids);
        Map<Long, Edge> longEdgeMap = new HashMap<>();

        for (var j : a) {
            if (!longEdgeMap.containsKey(j.getId())) {
                longEdgeMap.put(j.getId(), j);
            }
        }
        return longEdgeMap;
    }
}
