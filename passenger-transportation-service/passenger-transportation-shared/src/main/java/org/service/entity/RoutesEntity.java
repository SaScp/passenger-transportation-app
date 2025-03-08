package org.service.entity;

public record RoutesEntity(String id, LocationEntity departureCity, LocationEntity arrivalCity, String departureTime, String arrivalTime, String type, Integer price) {


}
