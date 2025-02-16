package org.service.output_port;

public interface RevokeBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {

    void revoke(String id); // TODO add param type

}
