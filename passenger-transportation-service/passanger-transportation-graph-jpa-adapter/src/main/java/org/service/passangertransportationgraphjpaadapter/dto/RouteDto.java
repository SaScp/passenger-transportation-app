package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * DTO for {@link Route}
 */
@Getter
@Setter
@AllArgsConstructor
public class RouteDto implements Serializable {
    private String id;
    private LocationDto departureCity;
    private LocationDto arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<RouteStepDto> routeStepDtos;

    public static RouteDto fromRoute(Route route) {
        return new RouteDto(route.getId(),
                LocationDto.fromLocation(route.getDepartureCity()),
                LocationDto.fromLocation(route.getArrivalCity()),
                route.getDepartureTime(),
                route.getArrivalTime(),
                route.getRouteSteps().stream().map(RouteStepDto::fromRouteStep).toList());
    }
}