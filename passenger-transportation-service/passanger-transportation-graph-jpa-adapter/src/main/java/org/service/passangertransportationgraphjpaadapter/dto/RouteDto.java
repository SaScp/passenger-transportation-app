package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.service.passangertransportationgraphjpaadapter.model.Route;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

/**
 * DTO for {@link Route}
 */
@Getter
@Setter
@AllArgsConstructor
public class RouteDto implements Serializable {
    String id;
    LocationDto departureCity;
    LocationDto arrivalCity;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    List<RouteStepDto> routeSteps;

    public static RouteDto fromRoute(Route route) {
        return new RouteDto(route.getId(),
                LocationDto.fromLocation(route.getDepartureCity()),
                LocationDto.fromLocation(route.getArrivalCity()),
                route.getDepartureTime(),
                route.getArrivalTime(),
                route.getRouteSteps().stream().map(RouteStepDto::fromRouteStep).toList());
    }
}