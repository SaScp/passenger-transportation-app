package org.service.entity;

import java.util.List;

public record RoutesEntity(String id, LocationEntity departureCity, LocationEntity arrivalCity, String departureTime,
                           String arrivalTime, String type, Double price, List<RouteStepEntity> routeSteps) {


}
