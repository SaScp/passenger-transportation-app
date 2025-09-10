package org.service.output_port.jpa.api.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.jpa.v2.RouteService;
import org.service.output_port.v2.RouteServiceOutputPort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultRouteServiceAdapter implements RouteServiceOutputPort {

    private final RouteService routeService;

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return this.getClass();
    }
}
