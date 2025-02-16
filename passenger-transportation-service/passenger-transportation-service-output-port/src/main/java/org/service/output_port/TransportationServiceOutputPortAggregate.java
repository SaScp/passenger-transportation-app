package org.service.output_port;

public interface TransportationServiceOutputPortAggregate {

    CreateBookingTransportationServiceOutputPort getCreateBookingTransportationServiceOutputPort();

    RevokeBookingTransportationServiceOutputPort getRevokeBookingTransportationServiceOutputPort();

    FindByParamsTransportationServiceOutputPort getFindByParamsTransportationServiceOutputPort();

    FindAllTransportationServiceOutputPort getFindAllTransportationServiceOutputPort();

    FindByPhoneTransportationServiceOutputPort getFindByPhoneTransportationServiceOutputPort();
}
