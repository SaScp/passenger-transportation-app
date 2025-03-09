package org.service.output_purt.jpa;

import lombok.AllArgsConstructor;
import org.service.output_port.entity.RouteStepEntity;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPurt;
import org.service.output_purt.mapper.RouteStepMapper;
import org.service.output_purt.repository.RouteStepRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationFindByRouteStepsIdsAdapter implements FindByRouteStepsIdsTransportationServiceOutputPurt {

    private final RouteStepRepository routeStepRepository;

    @Override
    @Cacheable(key = "#ids.hashCode()", value = "TransportationFindByRouteStepsIdsAdapter::findRouteStepsByIds")
    public List<RouteStepEntity> findRouteStepsByIds(List<String> ids) {
        return RouteStepMapper.INSTANCE.routeStepsToRouteStepEntitys(routeStepRepository.findRouteStepsByRouteIdIn(ids));
    }
}
