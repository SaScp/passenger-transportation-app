package org.service.output_port.cache;


import org.service.entity.RouteStepEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPort;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CacheTransportationFindByRouteStepsIdsAdapter extends CacheTransportationServiceOutputPort<FindByRouteStepsIdsTransportationServiceOutputPort>
        implements FindByRouteStepsIdsTransportationServiceOutputPort {


    public CacheTransportationFindByRouteStepsIdsAdapter(FindByRouteStepsIdsTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable(key = "#ids.hashCode()", value = "TransportationFindByRouteStepsIdsAdapter::findRouteStepsByIds")
    public List<RouteStepEntity> findRouteStepsByIds(List<String> ids) {
        return delegate.findRouteStepsByIds(ids);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByRouteStepsIdsTransportationServiceOutputPort.class;
    }
}
