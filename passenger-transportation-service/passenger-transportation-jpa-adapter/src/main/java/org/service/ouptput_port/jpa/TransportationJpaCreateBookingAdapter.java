package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.BookingParamsEntity;
import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransportationJpaCreateBookingAdapter implements CreateBookingTransportationServiceOutputPort {
    @Override
    public void create(BookingParamsEntity tt) {

    }
}
