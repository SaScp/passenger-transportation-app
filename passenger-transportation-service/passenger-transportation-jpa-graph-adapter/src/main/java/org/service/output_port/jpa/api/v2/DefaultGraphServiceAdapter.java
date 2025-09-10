package org.service.output_port.jpa.api.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.jpa.v2.GraphService;
import org.service.output_port.v2.GraphServiceOutputPort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultGraphServiceAdapter implements GraphServiceOutputPort {

    private final GraphService graphService;

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return this.getClass();
    }
}
