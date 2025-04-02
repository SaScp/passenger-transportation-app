package org.service.output_port.cache;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CacheTransportationJpaFindByParamAdapter  extends CacheTransportationServiceOutputPort<FindByParamsTransportationServiceOutputPort>
        implements FindByParamsTransportationServiceOutputPort {


    public CacheTransportationJpaFindByParamAdapter(FindByParamsTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable(key = "#entity.hashCode() - #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {
        return delegate.findBy(entity, pageEntity);
    }


    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByParamsTransportationServiceOutputPort.class;
    }
}
