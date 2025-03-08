package org.service.output_port.entity;


import java.io.Serializable;



public record EdgeEntity(String fromLocationId, String toLocationId) implements Serializable {

}