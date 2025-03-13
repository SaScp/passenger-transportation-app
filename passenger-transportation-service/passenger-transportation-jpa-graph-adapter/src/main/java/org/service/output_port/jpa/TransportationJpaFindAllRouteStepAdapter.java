package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.EdgeEntity;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.output_port.mapper.EdgeMapper;
import org.service.output_port.model.Edge;
import org.service.output_port.model.RouteStep;
import org.service.output_port.repository.EdgeRepository;
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
    private final EdgeRepository edgeRepository;

    @Override
    @Cacheable("TransportationJpaFindAllAdapter::findAll")
    public List<EdgeEntity> findAll() {
        List<RouteStep> routeSteps = repository.findAll();
        List<Edge> all = edgeRepository.findAll();
        return EdgeMapper.INSTANCE.edgesToEdgeEntitys(all);
    }
}
