package org.service.output_port.util;

import org.service.output_port.model.Edge;
import org.service.output_port.model.RouteEntityTemp;
import org.service.output_port.repository.EdgeRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EdgeUtils {

    private final EdgeRepository edgeRepository;

    public EdgeUtils(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }


    public Map<Long, Edge> getLongEdgeMap(List<RouteEntityTemp> recursiveResults) {
        List<String> ids = new ArrayList<>();
        recursiveResults.forEach(row -> {
            Collections.addAll(ids, row.path());
        });
        var a = edgeRepository.findAllByIdIn(ids);
        Map<Long, Edge> idsEl = new HashMap<>();

        for (var j : a) {
            if (!idsEl.containsKey(j.getId())) {
                idsEl.put(j.getId(), j);
            }
        }
        return idsEl;
    }
}
