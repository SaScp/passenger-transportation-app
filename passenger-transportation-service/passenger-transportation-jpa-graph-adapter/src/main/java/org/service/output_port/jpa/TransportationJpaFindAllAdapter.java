package org.service.output_port.jpa;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class TransportationJpaFindAllAdapter implements FindAllTransportationServiceOutputPort {

    private final RouteRepository repository;

    public TransportationJpaFindAllAdapter(RouteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(key = "#pageEntity.hashCode()", value = "TransportationJpaFindAllAdapter::findAll")
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        Page<String> routes = repository
                .findIds(PageRequest
                        .of(pageEntity.getPageNum(),
                                pageEntity.getPageSize()
                        )
                );
        return RouteMapper.INSTANCE.routesToRouteEntitys(repository.findRoutesByIdIn(routes.getContent(), Sort.by(Sort.Order.by("departureTime"))));
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllTransportationServiceOutputPort.class;
    }
}
