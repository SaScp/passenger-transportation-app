package org.service.entity;


import java.io.Serializable;



public record EdgeEntity(LocationEntity fromLocationId, LocationEntity toLocationId, String type) implements Serializable {

}