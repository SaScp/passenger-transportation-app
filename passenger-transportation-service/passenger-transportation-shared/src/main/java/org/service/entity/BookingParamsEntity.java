package org.service.entity;

import java.io.Serializable;

public record BookingParamsEntity(String numberPhone, String routeId) implements Serializable {

}
