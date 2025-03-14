package org.service.entity;


import java.io.Serializable;



public record EdgeEntity(Long id, LocationEntity fromLocationId, LocationEntity toLocationId, String type) implements Serializable {

}