package org.service.entity;

public record RouteStepEntity(
        String routeId,
        Integer routeStep,
        EdgeEntity edgeId) {
}
