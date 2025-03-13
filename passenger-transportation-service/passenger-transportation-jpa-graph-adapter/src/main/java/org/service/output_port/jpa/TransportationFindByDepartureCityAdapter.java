package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationFindByDepartureCityAdapter implements FindAllRoutesByDepartureCityOutputPort {

    private final RouteRepository repository;


    @Override
    @Cacheable(key = "#id + '_' + #pageEntity.hashCode()", value = "TransportationFindByDepartureCityAdapter::findAllByDepartureCity")
    public List<RoutesEntity> findAllByDepartureCity(String id, PageEntity pageEntity) {
        repository.findAllRecursiveRoutes(id, pageEntity.getPageNum(), pageEntity.getPageSize() * pageEntity.getPageNum());
        /*return RouteMapper.INSTANCE.routesToRouteEntitys(
                repository.findAllRecursiveRoutes(id, pageEntity.getPageNum(), pageEntity.getPageSize() * pageEntity.getPageNum())
        );*/
        return null;
    }
}
