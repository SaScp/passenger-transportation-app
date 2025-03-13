package org.service.output_port.find;

import org.service.entity.EdgeEntity;
import org.service.output_port.TransportationServiceOutputPort;

import java.util.List;

public interface FindAllRouteStepTransportationServiceOutputPort extends TransportationServiceOutputPort {

    public List<EdgeEntity> findAll();
}
