package org.service.output_port.cache;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CacheTransportationFindByDepartureCityAdapter extends CacheTransportationServiceOutputPort<FindAllRoutesByDepartureCityOutputPort>
        implements FindAllRoutesByDepartureCityOutputPort {


    public CacheTransportationFindByDepartureCityAdapter(FindAllRoutesByDepartureCityOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable(key = "#id + '_' + #pageEntity.hashCode()", value = "TransportationFindByDepartureCityAdapter::findAllByDepartureCity")
    public List<RoutesEntity> findAllByDepartureCity(String id, PageEntity pageEntity) {
        return delegate.findAllByDepartureCity(id, pageEntity);
    }



    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllRoutesByDepartureCityOutputPort.class;
    }
}
