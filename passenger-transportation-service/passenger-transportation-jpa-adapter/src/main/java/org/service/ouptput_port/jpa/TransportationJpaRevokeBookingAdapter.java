package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.output_port.RevokeBookingTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransportationJpaRevokeBookingAdapter implements RevokeBookingTransportationServiceOutputPort {
    @Override
    public void revoke(String id) {

    }
}
