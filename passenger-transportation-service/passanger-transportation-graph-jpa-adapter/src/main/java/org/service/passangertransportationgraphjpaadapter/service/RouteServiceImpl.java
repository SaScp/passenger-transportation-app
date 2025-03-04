package org.service.passangertransportationgraphjpaadapter.service;

import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;
import org.service.passangertransportationgraphjpaadapter.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;


    @Override
    public List<RouteDto> getByDepartureId(String id) {
        return routeRepository.findRoutesByDepartureCityId(id).stream().map(RouteDto::fromRoute).toList();
    }
}
