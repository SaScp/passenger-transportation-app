package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.EdgeEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.output_port.mapper.EdgeMapper;
import org.service.output_port.model.Edge;
import org.service.output_port.repository.EdgeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationJpaFindAllRouteStepAdapter implements FindAllRouteStepTransportationServiceOutputPort {

    private final EdgeRepository edgeRepository;

    @Override
    @Cacheable("TransportationJpaFindAllAdapter::findAll")
    public List<EdgeEntity> findAll() {
        List<Edge> all = edgeRepository.findAll();
        return EdgeMapper.INSTANCE.edgesToEdgeEntitys(all);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllRouteStepTransportationServiceOutputPort.class;
    }
}
