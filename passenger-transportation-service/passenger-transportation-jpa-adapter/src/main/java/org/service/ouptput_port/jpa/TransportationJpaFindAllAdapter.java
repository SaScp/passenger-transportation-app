package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.repository.RouteRepository;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindAllAdapter implements FindAllTransportationServiceOutputPort {

    private final RouteRepository repository;

    @Override
    public List<RoutesEntity> findAll() {
        return RouteMapper.INSTANCE.routesToRouteEntitys(repository.findAll(Sort.by(Sort.Order.by("departureTime"))));
    }
}
