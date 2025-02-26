package org.service.output_port;

import org.service.entity.BookingParamsEntity;

public interface CreateBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {

    /**
     * @param entity данный параметр содержит данные для создания новой брони
     * **/
    void create(BookingParamsEntity entity);

}
