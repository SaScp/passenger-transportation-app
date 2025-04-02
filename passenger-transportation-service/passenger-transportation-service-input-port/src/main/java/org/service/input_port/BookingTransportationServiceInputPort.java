package org.service.input_port;

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;

import java.util.List;

public interface BookingTransportationServiceInputPort {
    void createBooking(BookingParamsEntity bookingParams);

    void revokeBooking(String id);

    List<BookingEntity> findByPhone(String phone, PageEntity pageEntity);
}
