package org.service.output_port.entity;

public record RouteStepEntity(
        String routeId,
        Integer routeStep,
        EdgeEntity edgeId) {
}
