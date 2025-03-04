package org.service.passangertransportationgraphjpaadapter.service;

import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;

import java.util.List;

public interface RouteService {


    public List<RouteDto> getByDepartureId(String id);
}
