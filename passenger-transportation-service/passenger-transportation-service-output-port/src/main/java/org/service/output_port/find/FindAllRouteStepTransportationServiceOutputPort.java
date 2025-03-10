package org.service.output_port.find;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.entity.RouteStepEntity;

import java.util.List;

public interface FindAllRouteStepTransportationServiceOutputPort extends TransportationServiceOutputPort {

    public List<RouteStepEntity> findAll();
}
