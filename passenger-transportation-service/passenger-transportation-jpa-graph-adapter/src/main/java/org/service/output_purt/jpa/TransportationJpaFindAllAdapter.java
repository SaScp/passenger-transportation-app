package org.service.output_purt.jpa;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_purt.mapper.RouteMapper;
import org.service.output_purt.model.Route;
import org.service.output_purt.repository.RouteRepository;
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
                                pageEntity.getPageSize(),
                                Sort.by(Sort.Order.by("departureTime"))
                        )
                );
        return RouteMapper.INSTANCE.routesToRouteEntitys(repository.findRoutesByIdIn(routes.getContent()));
    }
}
