package org.service.input_port;

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BookingTransportationServiceInputPort {
    void createBooking(BookingParamsEntity bookingParams);

    void revokeBooking(String id);

    CompletableFuture<List<BookingEntity>> findByPhone(String phone, PageEntity pageEntity);
}
