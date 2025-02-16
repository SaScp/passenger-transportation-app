package org.service.input_port;

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import java.util.List;
import java.util.Set;

public interface TransportationServiceInputPort {


    List<RoutesEntity> findByParams(ParamsEntity entity);

    List<RoutesEntity> findAll();

    void createBooking(BookingParamsEntity bookingParams);

    void revokeBooking(String id);

    List<BookingEntity> findByPhone(String phone);
}
