package org.service.output_purt.jpa;

import lombok.AllArgsConstructor;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.entity.RouteStepEntity;
import org.service.output_purt.mapper.RouteStepMapper;
import org.service.output_purt.model.RouteStep;
import org.service.output_purt.repository.RouteStepRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransportationJpaFindAllRouteStepAdapter implements FindAllRouteStepTransportationServiceOutputPort {

    private final RouteStepRepository repository;

    @Override
    //@Cacheable(key = "#result", value = "TransportationJpaFindAllAdapter::findAll")
    public List<RouteStepEntity> findAll() {
        List<RouteStep> routeSteps = repository.findAll();
        return RouteStepMapper.INSTANCE.routeStepsToRouteStepEntitys(routeSteps);
    }
}
