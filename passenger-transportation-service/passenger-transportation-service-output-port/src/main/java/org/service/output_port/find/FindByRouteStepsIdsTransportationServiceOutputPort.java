package org.service.output_port.find;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.entity.RouteStepEntity;

import java.util.List;

public interface FindByRouteStepsIdsTransportationServiceOutputPort extends TransportationServiceOutputPort {

    List<RouteStepEntity> findRouteStepsByIds(List<String> ids);
}
