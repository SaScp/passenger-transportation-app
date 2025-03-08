package org.service.output_port.create;

import org.service.entity.BookingParamsEntity;
import org.service.output_port.TransportationServiceOutputPort;

public interface CreateBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {

    /**
     * @param entity данный параметр содержит данные для создания новой брони
     * **/
    void create(BookingParamsEntity entity);

}
