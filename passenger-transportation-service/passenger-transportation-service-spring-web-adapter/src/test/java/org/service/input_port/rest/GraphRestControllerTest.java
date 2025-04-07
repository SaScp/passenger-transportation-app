package org.service.input_port.rest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.service.entity.GraphEntity;
import org.service.input_port.GraphTransportationServiceInputPort;

public  class GraphRestControllerTest {

    private GraphTransportationServiceInputPort inputPort;
    private GraphRestController controller;
    private GraphEntity defaultGraph;

    @BeforeEach
    void setUp() {
        inputPort = mock(GraphTransportationServiceInputPort.class);
        controller = new GraphRestController(inputPort);


        Set<Map<String, String>> nodes = new HashSet<>(Arrays.asList(
                Map.of("id", "mockNode1", "label", "Mock City A"),
                Map.of("id", "mockNode2", "label", "Mock City B"),
                Map.of("id", "mockNode3", "label", "Mock City C")
        ));
        Set<Map<String, String>> edges = new HashSet<>(Arrays.asList(
                Map.of("id", "mockEdge1", "from", "mockNode1", "to", "mockNode2", "type", "BUS"),
                Map.of("id", "mockEdge2", "from", "mockNode2", "to", "mockNode3", "type", "TRAIN")
        ));
        defaultGraph = new GraphEntity(nodes, edges);
    }

    @Test
    void testFindAllTransport() throws Exception {

        when(inputPort.findAll()).thenReturn(CompletableFuture.completedFuture(defaultGraph));

        CompletableFuture<GraphEntity> future = controller.findAllTransport();
        GraphEntity result = future.get();

        assertNotNull(result);
        assertEquals(defaultGraph.nodes().size(), result.nodes().size());
        assertEquals(defaultGraph.edges().size(), result.edges().size());
        verify(inputPort, times(1)).findAll();
    }

    @Test
    void testFindGraphByIdsWithValidIds() throws Exception {

        List<String> ids = Arrays.asList("mockNode1", "mockEdge2");


        Set<Map<String, String>> expectedNodes = new HashSet<>();
        expectedNodes.add(Map.of("id", "mockNode1", "label", "Mock City A"));
        Set<Map<String, String>> expectedEdges = new HashSet<>();
        expectedEdges.add(Map.of("id", "mockEdge2", "from", "mockNode2", "to", "mockNode3", "type", "TRAIN"));
        GraphEntity expectedGraph = new GraphEntity(expectedNodes, expectedEdges);

        when(inputPort.findGraphByIds(ids))
                .thenReturn(CompletableFuture.completedFuture(expectedGraph));

        CompletableFuture<GraphEntity> future = controller.findGraphByIds(ids);
        GraphEntity result = future.get();

        assertNotNull(result);
        assertEquals(expectedNodes.size(), result.nodes().size());
        assertEquals(expectedEdges.size(), result.edges().size());
        assertTrue(result.nodes().containsAll(expectedNodes));
        assertTrue(result.edges().containsAll(expectedEdges));
        verify(inputPort, times(1)).findGraphByIds(ids);
    }

    @Test
    void testFindGraphByIdsWithEmptyList() throws Exception {

        List<String> ids = Collections.emptyList();
        GraphEntity expectedGraph = new GraphEntity(Collections.emptySet(), Collections.emptySet());

        when(inputPort.findGraphByIds(ids))
                .thenReturn(CompletableFuture.completedFuture(expectedGraph));

        CompletableFuture<GraphEntity> future = controller.findGraphByIds(ids);
        GraphEntity result = future.get();

        assertNotNull(result);
        assertTrue(result.nodes().isEmpty());
        assertTrue(result.edges().isEmpty());
        verify(inputPort, times(1)).findGraphByIds(ids);
    }

    @Test
    void testFindGraphByIdsWithNonMatchingIds() throws Exception {

        List<String> ids = Arrays.asList("nonexistent1", "nonexistent2");
        GraphEntity expectedGraph = new GraphEntity(Collections.emptySet(), Collections.emptySet());

        when(inputPort.findGraphByIds(ids))
                .thenReturn(CompletableFuture.completedFuture(expectedGraph));

        CompletableFuture<GraphEntity> future = controller.findGraphByIds(ids);
        GraphEntity result = future.get();

        assertNotNull(result);
        assertTrue(result.nodes().isEmpty());
        assertTrue(result.edges().isEmpty());
        verify(inputPort, times(1)).findGraphByIds(ids);
    }

    @Test
    void testFindGraphByIdsWithNull() throws Exception {

        List<String> ids = null;
        GraphEntity expectedGraph = new GraphEntity(Collections.emptySet(), Collections.emptySet());

        when(inputPort.findGraphByIds(ids))
                .thenReturn(CompletableFuture.completedFuture(expectedGraph));

        CompletableFuture<GraphEntity> future = controller.findGraphByIds(ids);
        GraphEntity result = future.get();

        assertNotNull(result);
        assertTrue(result.nodes().isEmpty());
        assertTrue(result.edges().isEmpty());
        verify(inputPort, times(1)).findGraphByIds(ids);
    }
}
