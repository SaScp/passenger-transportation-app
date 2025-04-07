package org.service.output_port.cache;


import org.service.entity.TypeEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component

public class CacheTransportationJpaFindTypesAdapter extends CacheTransportationServiceOutputPort<FindTypesTransportationServiceOutputPort>
        implements FindTypesTransportationServiceOutputPort {


    public CacheTransportationJpaFindTypesAdapter(FindTypesTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable("TransportationJpaFindTypesAdapter::findAllTypeEntity")
    public List<TypeEntity> findAllTypeEntity() {
        return delegate.findAllTypeEntity();
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindTypesTransportationServiceOutputPort.class;
    }
}
