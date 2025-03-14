package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;

import java.io.Serializable;

/**
 * DTO for {@link RouteStep}
 */

@Getter
@Setter
@AllArgsConstructor
public class RouteStepDto implements Serializable {
    String routeId;
    Integer routeStep;
    EdgeDto edgeId;


    public static RouteStepDto fromRouteStep(RouteStep route) {
        return new RouteStepDto(route.getRouteId(), route.getRouteStep(), EdgeDto.fromEdge(route.getEdgeId()));
    }
}