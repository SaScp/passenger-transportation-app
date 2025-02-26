package org.service.output_port;



/**
 * Класс для агрегации всех портов и дальнейшего использования в ядре(бизнес логике) приложения
 * **/
public interface TransportationServiceOutputPortAggregate {

    CreateBookingTransportationServiceOutputPort getCreateBookingTransportationServiceOutputPort();

    RevokeBookingTransportationServiceOutputPort getRevokeBookingTransportationServiceOutputPort();

    FindByParamsTransportationServiceOutputPort getFindByParamsTransportationServiceOutputPort();

    FindAllTransportationServiceOutputPort getFindAllTransportationServiceOutputPort();

    FindByPhoneTransportationServiceOutputPort getFindByPhoneTransportationServiceOutputPort();

    FindTypesTransportationServiceOutputPort getFindTypesTransportationServiceOutputPort();
}
