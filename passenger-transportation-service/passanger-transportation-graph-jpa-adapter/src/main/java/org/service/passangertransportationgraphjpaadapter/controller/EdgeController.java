package org.service.passangertransportationgraphjpaadapter.controller;

import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.BookingEntity;
import org.service.passangertransportationgraphjpaadapter.BookingQueryParam;
import org.service.passangertransportationgraphjpaadapter.FilterParamEntity;
import org.service.passangertransportationgraphjpaadapter.FindByParam;
import org.service.passangertransportationgraphjpaadapter.dto.*;
import org.service.passangertransportationgraphjpaadapter.service.EdgeService;
import org.service.passangertransportationgraphjpaadapter.service.LocationService;
import org.service.passangertransportationgraphjpaadapter.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class EdgeController {

    private final EdgeService edgeService;

    private final LocationService locationService;

    private final RouteService routeService;

    @GetMapping("/graph")
    public Graph getGraph() {
        return routeService.getAll();
    }

    @GetMapping("/location")
    public List<LocationDto> getLocation() {
        return locationService.getLocations();
    }

    @GetMapping("/route")
    public List<RouteDto> getRoute(@FindByParam FilterParamEntity filterParam) {
        System.out.println(filterParam.getType());
        return routeService.getByParams( new ParamsEntity(
                filterParam.getTime(),
                filterParam.getType(),
                filterParam.getFrom(),
                filterParam.getTo()
        ));
    }

    @GetMapping("/edges-by-id")
    public Graph getEdgesById(@RequestParam("id") List<String> ids) {
        return routeService.getGraphByIds(ids);
    }

    @GetMapping("/route-by-id")
    public List<RouteDto> getRoutes(@RequestParam("id") String id) {
        return routeService.getByDepartureId(id);
    }

    @GetMapping("/types")
    public List<TypeDto> getTypes() {
        return routeService.getTypes();
    }


  /*  @PostMapping(value = "/create")
    public CompletableFuture<ResponseEntity<?>> booking(@RequestBody BookingQueryParam query) {

        return CompletableFuture.supplyAsync(() -> ResponseEntity.created(URI.create("/create")).build());
    }*/

    @GetMapping("/find-by-phone")
    public List<BookingEntity> findTransportByPhone(@RequestParam(value = "phone") String phone) {
        return routeService.findBy(phone.replace(" ", "+"));
    }
}
