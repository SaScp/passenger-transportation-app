package org.service.core;

import org.service.input_port.ServiceInputPort;
import org.service.output_port.ServiceOutputPort;

public class ServiceCore implements ServiceInputPort {

    private final ServiceOutputPort serviceOutputPort;

    public ServiceCore(ServiceOutputPort serviceOutputPort) {
        this.serviceOutputPort = serviceOutputPort;
    }
}
