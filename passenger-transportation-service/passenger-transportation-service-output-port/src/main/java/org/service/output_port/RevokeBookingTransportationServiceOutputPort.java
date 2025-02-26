package org.service.output_port;

public interface RevokeBookingTransportationServiceOutputPort extends TransportationServiceOutputPort {


    /**
     * @param id идентификатор отменённой поездки
     * **/
    void revoke(String id);

}
