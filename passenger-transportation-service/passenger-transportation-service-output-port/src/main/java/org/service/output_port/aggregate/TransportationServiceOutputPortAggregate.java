package org.service.output_port.aggregate;


import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.*;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

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

    FindAllRouteStepTransportationServiceOutputPort getFindAllRouteStepTransportationServiceOutputPort();

    FindByRouteStepsIdsTransportationServiceOutputPurt getFindByRouteStepsIdsTransportationServiceOutputPurt();
}
