package org.service.input_port.rest;

import org.service.input_port.TransportationServiceInputPort;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransportationRestController {

    private final TransportationServiceInputPort inputPort;

    public TransportationRestController(TransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
    }

}
