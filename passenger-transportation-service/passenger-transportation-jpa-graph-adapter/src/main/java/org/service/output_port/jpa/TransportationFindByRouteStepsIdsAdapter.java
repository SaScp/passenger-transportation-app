package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.RouteStepEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPort;
import org.service.output_port.mapper.RouteStepMapper;
import org.service.output_port.repository.RouteStepRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationFindByRouteStepsIdsAdapter implements FindByRouteStepsIdsTransportationServiceOutputPort {

    private final RouteStepRepository routeStepRepository;


    @Override
    @Cacheable(key = "#ids.hashCode()", value = "TransportationFindByRouteStepsIdsAdapter::findRouteStepsByIds")
    public List<RouteStepEntity> findRouteStepsByIds(List<String> ids) {
        return RouteStepMapper.INSTANCE.routeStepsToRouteStepEntitys(routeStepRepository.findRouteStepsByRouteIdIn(ids));
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByRouteStepsIdsTransportationServiceOutputPort.class;
    }
}
