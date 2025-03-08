package org.service.output_port.find;

import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.entity.RouteStepEntity;

import java.util.List;

public interface FindByRouteStepsIdsTransportationServiceOutputPurt extends TransportationServiceOutputPort {

    List<RouteStepEntity> findRouteStepsByIds(List<String> ids);
}
