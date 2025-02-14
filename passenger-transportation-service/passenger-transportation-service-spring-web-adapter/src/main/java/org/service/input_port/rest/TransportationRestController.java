package org.service.input_port.rest;

import org.service.entity.BookingParamsEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.input_port.TransportationServiceInputPort;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FilterParamEntity;
import org.service.input_port.request.BookingQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TransportationRestController {

    private final TransportationServiceInputPort inputPort;

    public TransportationRestController(TransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @GetMapping("/find")
    public List<RoutesEntity> findTransport(@FindByParam FilterParamEntity filterParam) {
        return this.inputPort.findByParams(
                new ParamsEntity(
                        filterParam.getTime(),
                        filterParam.getType(),
                        filterParam.getFrom(),
                        filterParam.getTo()
                )
        );
    }

    @GetMapping("/find-all")
    public List<RoutesEntity> findAllTransport() {
        return this.inputPort.findAll();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> booking(@RequestBody BookingQueryParam query) {
        return this.inputPort.createBooking(
                new BookingParamsEntity(query.getNumberPhone(), query.getRouteId()))?
                ResponseEntity.created(URI.create("/create")).build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("/revoke")
    public void revokeBooking(@RequestParam(name = "booking_id") String id) {
        System.out.println(id);
    }

}
