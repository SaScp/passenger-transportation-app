package org.service.output_port;

import org.service.entity.BookingParamsEntity;

public interface CreateBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {
    void create(BookingParamsEntity tt); // TODO add param type

}
