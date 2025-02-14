package org.service.core;

import org.service.entity.BookingParamsEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransportationServiceCore implements TransportationServiceInputPort {

   private final TransportationServiceOutputPortAggregate aggregate;


    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity) {
        return aggregate.getFindByParamsTransportationServiceOutputPort().findBy(entity);
    }

    @Override
    public List<RoutesEntity> findAll() {
        return aggregate.getFindAllTransportationServiceOutputPort().findAll();
    }

    @Override
    public Boolean createBooking(BookingParamsEntity bookingParams) {
        return aggregate.getCreateBookingTransportationServiceOutputPort().create(bookingParams);
    }
}
