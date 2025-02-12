package org.service.core;

import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.TransportationServiceOutputPort;

public class TransportationServiceCore implements TransportationServiceInputPort {

    private final TransportationServiceOutputPort serviceOutputPort;

    public TransportationServiceCore(TransportationServiceOutputPort serviceOutputPort) {
        this.serviceOutputPort = serviceOutputPort;
    }
}
