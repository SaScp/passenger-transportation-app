package org.service.output_port;

import org.service.entity.BookingParamsEntity;

public interface CreateBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {
    boolean create(BookingParamsEntity tt); // TODO add param type

}
