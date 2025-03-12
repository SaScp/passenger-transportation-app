package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.entity.RouteStepEntity;
import org.service.output_port.mapper.RouteStepMapper;
import org.service.output_port.model.RouteStep;
import org.service.output_port.repository.RouteStepRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransportationJpaFindAllRouteStepAdapter implements FindAllRouteStepTransportationServiceOutputPort {

    private final RouteStepRepository repository;

    @Override
    @Cacheable("TransportationJpaFindAllAdapter::findAll")
    public List<RouteStepEntity> findAll() {
        List<RouteStep> routeSteps = repository.findAll();
        return RouteStepMapper.INSTANCE.routeStepsToRouteStepEntitys(routeSteps);
    }
}
