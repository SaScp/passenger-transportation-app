package org.service.output_port.cache;


import org.service.entity.BookingParamsEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;

import org.springframework.stereotype.Component;


@Component
public class CacheTransportationJpaCreateBookingAdapter extends CacheTransportationServiceOutputPort<CreateBookingTransportationServiceOutputPort> implements CreateBookingTransportationServiceOutputPort  {


    public CacheTransportationJpaCreateBookingAdapter(CreateBookingTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    public void create(BookingParamsEntity entity) {
        delegate.create(entity);
    }


    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return CreateBookingTransportationServiceOutputPort.class;
    }
}
