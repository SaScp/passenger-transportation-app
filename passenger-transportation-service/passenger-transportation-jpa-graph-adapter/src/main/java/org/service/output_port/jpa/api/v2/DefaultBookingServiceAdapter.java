package org.service.output_port.jpa.api.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.jpa.v2.BookingService;
import org.service.output_port.v2.BookingServiceOutputPort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultBookingServiceAdapter implements BookingServiceOutputPort {

    private final BookingService bookingService;

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return this.getClass();
    }
}
