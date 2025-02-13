package org.service.input_port.rest;

import org.service.input_port.TransportationServiceInputPort;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FindByParamEntity;
import org.service.input_port.request.RequstQuery;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
public class TransportationRestController {

  /*  private final TransportationServiceInputPort inputPort;

    public TransportationRestController(TransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
    }*/

    @GetMapping("/find")
    public void findTransport(@FindByParam FindByParamEntity findByParam) {
        System.out.println(findByParam.getType());
        System.out.println(findByParam.getZonedDateTime());
        System.out.println(findByParam.getTo());
        System.out.println(findByParam.getFrom());
    }

    @GetMapping("/find-all")
    public void findAllTransport() {
        System.out.println("ok");
    }

    @PostMapping("/create")
    public void booking(@RequestBody RequstQuery query) {
        System.out.println(query);
    }

    @PostMapping("/revoke")
    public void revokeBooking(@RequestParam(name = "booking_id") String id) {
        System.out.println(id);
    }

}
