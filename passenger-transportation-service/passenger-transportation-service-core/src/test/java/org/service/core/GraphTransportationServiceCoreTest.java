package org.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.service.entity.EdgeEntity;
import org.service.entity.GraphEntity;
import org.service.entity.RouteStepEntity;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GraphTransportationServiceCoreTest {

    @Mock
    private TransportationServiceOutputPortAggregate aggregate;

    @Mock
    private FindAllRouteStepTransportationServiceOutputPort findAllRouteStepOutputPort;

    @Mock
    private FindByRouteStepsIdsTransportationServiceOutputPort findByRouteStepsIdsOutputPort;

    private GraphTransportationServiceCore graphCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(aggregate.getOutputPort(FindAllRouteStepTransportationServiceOutputPort.class))
                .thenReturn(findAllRouteStepOutputPort);
        when(aggregate.getOutputPort(FindByRouteStepsIdsTransportationServiceOutputPort.class))
                .thenReturn(findByRouteStepsIdsOutputPort);
        graphCore = new GraphTransportationServiceCore(aggregate);
    }

    @Test
    void testFindAllGraphEmpty() {
        // При отсутствии шагов маршрута фабрика должна вернуть пустые наборы
        when(findAllRouteStepOutputPort.findAll()).thenReturn(Collections.emptyList());

        GraphEntity graph = graphCore.findAll();
        assertNotNull(graph);
        assertTrue(graph.nodes().isEmpty());
        assertTrue(graph.edges().isEmpty());
    }

    @Test
    void testFindGraphByIdsEmpty() {
        when(findByRouteStepsIdsOutputPort.findRouteStepsByIds(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        GraphEntity graph = graphCore.findGraphByIds(Collections.emptyList());
        assertNotNull(graph);
        assertTrue(graph.nodes().isEmpty());
        assertTrue(graph.edges().isEmpty());
    }


}
