package org.service.core;

import org.service.entity.*;
import org.service.exception.ProblemDetailsException;
import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.*;

import java.awt.print.PrinterAbortException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransportationServiceCore implements TransportationServiceInputPort {

   private final TransportationServiceOutputPortAggregate aggregate;


    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        return aggregate.getFindByParamsTransportationServiceOutputPort().findBy(entity, pageEntity);
    }

    @Override
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return aggregate.getFindAllTransportationServiceOutputPort().findAll(pageEntity);
    }

    @Override
    public void createBooking(BookingParamsEntity bookingParams) {
        try {
            aggregate.getCreateBookingTransportationServiceOutputPort().create(bookingParams);
        } catch (ProblemDetailsException e) {
            throw new ProblemDetailsException(e);
        }
    }

    @Override
    public void revokeBooking(String id) {
         aggregate.getRevokeBookingTransportationServiceOutputPort().revoke(id);
    }

    @Override
    public List<BookingEntity> findByPhone(String phone) {
        phone = phone.replace(" ", "+");
        return aggregate.getFindByPhoneTransportationServiceOutputPort().findBy(phone);
    }
}
