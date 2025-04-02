package org.service.output_port.cache;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component

public class CacheTransportationJpaFindAllAdapter extends CacheTransportationServiceOutputPort<FindAllTransportationServiceOutputPort>
        implements FindAllTransportationServiceOutputPort {


    public CacheTransportationJpaFindAllAdapter(FindAllTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable(key = "#pageEntity.hashCode()", value = "TransportationJpaFindAllAdapter::findAll")
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return delegate.findAll(pageEntity);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindAllTransportationServiceOutputPort.class;
    }
}
