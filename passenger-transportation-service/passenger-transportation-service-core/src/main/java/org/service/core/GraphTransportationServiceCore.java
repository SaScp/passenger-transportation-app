package org.service.core;

import org.service.core.factory.Type;
import org.service.core.factory.edge.EdgeResponseFactory;
import org.service.core.factory.route_step.RouteStepResponseFactory;
import org.service.entity.EdgeEntity;
import org.service.entity.GraphEntity;
import org.service.entity.RouteStepEntity;
import org.service.input_port.GraphTransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPort;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


public class GraphTransportationServiceCore extends TransportationServiceCore implements GraphTransportationServiceInputPort {

    public GraphTransportationServiceCore(TransportationServiceOutputPortAggregate aggregate, ExecutorService executorService) {
        super(aggregate, executorService);
    }

    @Override
    public CompletableFuture<GraphEntity> findAll() {

        return CompletableFuture.supplyAsync(() -> {
            List<EdgeEntity> routeSteps = aggregate.getOutputPort(FindAllRouteStepTransportationServiceOutputPort.class).findAll();
            Set<Map<String, String>> nodes = EdgeResponseFactory.createResponsePart(routeSteps, Type.NODE);
            Set<Map<String, String>> edges = EdgeResponseFactory.createResponsePart(routeSteps, Type.EDGE);
            return new GraphEntity(nodes, edges);
        }, executorService);
    }

    @Override
    public CompletableFuture<GraphEntity> findGraphByIds(List<String> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<RouteStepEntity> routeStepsByIds = aggregate.getOutputPort(FindByRouteStepsIdsTransportationServiceOutputPort.class).findRouteStepsByIds(ids);
            Set<Map<String, String>> nodes = RouteStepResponseFactory.createResponsePart(routeStepsByIds, Type.NODE);
            Set<Map<String, String>> edges = RouteStepResponseFactory.createResponsePart(routeStepsByIds, Type.EDGE);
            return new GraphEntity(nodes, edges);
        }, executorService);
    }
}
