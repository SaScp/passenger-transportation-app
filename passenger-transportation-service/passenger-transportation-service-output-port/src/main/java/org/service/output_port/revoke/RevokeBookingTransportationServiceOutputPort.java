package org.service.output_port.revoke;

import org.service.output_port.TransportationServiceOutputPort;

public interface RevokeBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {

    /**
     * @param id идентификатор отменённой поездки
     * **/
    void revoke(String id);

}
