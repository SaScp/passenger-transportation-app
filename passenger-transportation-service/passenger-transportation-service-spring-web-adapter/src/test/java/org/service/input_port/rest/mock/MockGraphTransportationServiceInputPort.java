package org.service.input_port.rest.mock;


import org.service.entity.GraphEntity;
import org.service.input_port.GraphTransportationServiceInputPort;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * A mock implementation of GraphTransportationServiceInputPort for testing purposes.
 * Returns predefined graph data.
 */
public class MockGraphTransportationServiceInputPort implements GraphTransportationServiceInputPort {

    private final GraphEntity defaultGraph;

    public MockGraphTransportationServiceInputPort() {

        Set<Map<String, String>> nodes = new HashSet<>(Arrays.asList(
                Map.of("id", "mockNode1", "label", "Mock City A"),
                Map.of("id", "mockNode2", "label", "Mock City B"),
                Map.of("id", "mockNode3", "label", "Mock City C")
        ));
        Set<Map<String, String>> edges = new HashSet<>(Arrays.asList(
                Map.of("id", "mockEdge1", "from", "mockNode1", "to", "mockNode2", "type", "BUS"),
                Map.of("id", "mockEdge2", "from", "mockNode2", "to", "mockNode3", "type", "TRAIN")
        ));
        this.defaultGraph = new GraphEntity(nodes, edges);
    }

    @Override
    public CompletableFuture<GraphEntity> findAll() {
        System.out.println("Mock: Finding all graph data.");
        return CompletableFuture.completedFuture(defaultGraph);
    }

    @Override
    public CompletableFuture<GraphEntity> findGraphByIds(List<String> ids) {
        System.out.println("Mock: Finding graph data by ids: " + ids);
        if (ids == null || ids.isEmpty()) {
            return CompletableFuture.completedFuture(new GraphEntity(Collections.emptySet(), Collections.emptySet()));
        }


        Set<Map<String, String>> filteredNodes = defaultGraph.nodes().stream()
                .filter(node -> ids.contains(node.get("id")))
                .collect(Collectors.toSet());

        Set<Map<String, String>> filteredEdges = defaultGraph.edges().stream()
                .filter(edge -> ids.contains(edge.get("id")))
                .collect(Collectors.toSet());



        GraphEntity filteredGraph = new GraphEntity(filteredNodes, filteredEdges);
        return CompletableFuture.completedFuture(filteredGraph);
    }
}
