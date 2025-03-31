package org.service.entity;

import java.io.Serializable;

public record RouteStepEntity(
        String routeId,
        Integer routeStep,
        EdgeEntity edgeId) implements Serializable {
}
