package org.service.input_port.rest;

import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.input_port.TransportationServiceInputPort;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FilterParamEntity;
import org.service.input_port.request.RequestQuery;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public void booking(@RequestBody RequestQuery query) {
        System.out.println(query);
    }

    @PostMapping("/revoke")
    public void revokeBooking(@RequestParam(name = "booking_id") String id) {
        System.out.println(id);
    }

}
