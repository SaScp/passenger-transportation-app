package org.service.output_port.cache;

import org.service.entity.BookingEntity;
import org.service.entity.PageEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CacheTransportationJpaFindByPhoneAdapter extends CacheTransportationServiceOutputPort<FindByPhoneTransportationServiceOutputPort>
        implements FindByPhoneTransportationServiceOutputPort {


    public CacheTransportationJpaFindByPhoneAdapter(FindByPhoneTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    @Cacheable(key = "#phone.hashCode()", value = "TransportationJpaFindByPhoneAdapter::findBy")
    public List<BookingEntity> findBy(String phone, PageEntity pageEntity) {
        return delegate.findBy(phone, pageEntity);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByPhoneTransportationServiceOutputPort.class;
    }
}
