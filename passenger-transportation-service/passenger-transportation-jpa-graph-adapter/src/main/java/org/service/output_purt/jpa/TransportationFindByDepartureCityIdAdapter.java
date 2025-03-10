package org.service.output_purt.jpa;

import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_purt.mapper.RouteMapper;
import org.service.output_purt.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationFindByDepartureCityIdAdapter implements FindAllRoutesByDepartureCityOutputPort {

    private final RouteRepository repository;


    @Override
    @Cacheable(key = "#id + '_' + #pageEntity.hashCode()", value = "TransportationFindByDepartureCityIdAdapter::findAllByDepartureCityId")
    public List<RoutesEntity> findAllByDepartureCityId(String id, PageEntity pageEntity) {
        return RouteMapper.INSTANCE.routesToRouteEntitys(
                repository.findRoutesByIdIn(
                        repository.findRoutesIdByDepId(id, PageRequest.of(pageEntity.getPageNum(), pageEntity.getPageSize()))
                )
        );
    }
}
