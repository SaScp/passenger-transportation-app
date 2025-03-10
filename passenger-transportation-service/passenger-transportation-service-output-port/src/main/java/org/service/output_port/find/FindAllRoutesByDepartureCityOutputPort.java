package org.service.output_port.find;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;

import java.util.List;

public interface FindAllRoutesByDepartureCityOutputPort extends TransportationServiceOutputPort {

    List<RoutesEntity> findAllByDepartureCityId(String id, PageEntity pageEntity);
}
