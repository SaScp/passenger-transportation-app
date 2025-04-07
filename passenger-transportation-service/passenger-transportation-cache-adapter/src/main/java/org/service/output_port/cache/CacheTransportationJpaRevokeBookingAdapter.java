package org.service.output_port.cache;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;
import org.springframework.stereotype.Component;


@Component
public class CacheTransportationJpaRevokeBookingAdapter extends CacheTransportationServiceOutputPort<RevokeBookingTransportationServiceOutputPort>
        implements RevokeBookingTransportationServiceOutputPort {


    public CacheTransportationJpaRevokeBookingAdapter(RevokeBookingTransportationServiceOutputPort delegate) {
        super(delegate);
    }

    @Override
    public void revoke(String id) {
        delegate.revoke(id);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return RevokeBookingTransportationServiceOutputPort.class;
    }
}

