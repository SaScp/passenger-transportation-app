package org.service.output_port.entity;


import org.service.entity.LocationEntity;

import java.io.Serializable;



public record EdgeEntity(LocationEntity fromLocationId, LocationEntity toLocationId, Integer type) implements Serializable {

}