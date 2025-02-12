package org.service.input_port.rest;

import org.service.input_port.TransportationServiceInputPort;
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
    public void findTransport(@RequestParam(name = "time") ZonedDateTime time,
                              @RequestParam(name = "type") String type) {
        System.out.println(time);
        System.out.println(type);
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
