package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.repository.RouteRepository;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindAllAdapter implements FindAllTransportationServiceOutputPort {

    private final RouteRepository repository;

    @Override
    @Cacheable(key = "#pageEntity.hashCode()", value = "TransportationJpaFindAllAdapter::findAll")
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return RouteMapper.INSTANCE
                .routesToRouteEntitys(repository
                        .findAll(PageRequest
                                .of(pageEntity.getPageNum(),
                                        pageEntity.getPageSize(),
                                        Sort.by(Sort.Order.by("departureTime"))
                                )
                        ).stream()
                        .toList()
                );
    }
}
