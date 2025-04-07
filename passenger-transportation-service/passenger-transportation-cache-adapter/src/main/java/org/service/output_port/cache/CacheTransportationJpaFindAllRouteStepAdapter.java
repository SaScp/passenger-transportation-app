package org.service.output_port.cache;

import org.service.entity.EdgeEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CacheTransportationJpaFindAllRouteStepAdapter extends CacheTransportationServiceOutputPort<FindAllRouteStepTransportationServiceOutputPort>
        implements FindAllRouteStepTransportationServiceOutputPort {


    public CacheTransportationJpaFindAllRouteStepAdapter(FindAllRouteStepTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable("TransportationJpaFindAllRouteStepAdapter::findAll")
    public List<EdgeEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllRouteStepTransportationServiceOutputPort.class;
    }
}
