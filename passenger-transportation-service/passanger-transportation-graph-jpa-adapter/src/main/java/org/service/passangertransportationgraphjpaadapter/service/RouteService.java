package org.service.passangertransportationgraphjpaadapter.service;

import org.service.passangertransportationgraphjpaadapter.dto.Graph;
import org.service.passangertransportationgraphjpaadapter.dto.ParamsEntity;
import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;

import java.util.List;

public interface RouteService {


     List<RouteDto> getByParams(ParamsEntity id);


    Graph getGraphByIds(List<String> ids);

    Graph getAll();

    List<RouteDto> getByDepartureId(String id);

}
