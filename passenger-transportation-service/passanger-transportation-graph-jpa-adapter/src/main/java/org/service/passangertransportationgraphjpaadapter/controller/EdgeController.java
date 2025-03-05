package org.service.passangertransportationgraphjpaadapter.controller;

import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.EdgeDto;
import org.service.passangertransportationgraphjpaadapter.dto.LocationDto;
import org.service.passangertransportationgraphjpaadapter.dto.RouteDto;
import org.service.passangertransportationgraphjpaadapter.service.EdgeService;
import org.service.passangertransportationgraphjpaadapter.service.LocationService;
import org.service.passangertransportationgraphjpaadapter.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class EdgeController {

    private final EdgeService edgeService;

    private final LocationService locationService;

    private final RouteService routeService;

    @GetMapping("/graph")
    public List<EdgeDto> getGraph() {
        return edgeService.getEdges();
    }

    @GetMapping("/location")
    public List<LocationDto> getLocation() {
        return locationService.getLocations();
    }

    @GetMapping("/route")
    public Map<String, ?> getRoute(@RequestParam("id") String id) {
        return routeService.getByDepartureId(id);
    }
}
