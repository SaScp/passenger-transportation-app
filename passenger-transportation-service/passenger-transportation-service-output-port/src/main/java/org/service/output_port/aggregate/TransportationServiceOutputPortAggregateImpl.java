package org.service.output_port.aggregate;


import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.*;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

public class TransportationServiceOutputPortAggregateImpl implements TransportationServiceOutputPortAggregate {

    private final CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort;
    private final RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort;
    private final FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort;
    private final FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort;
    private final FindByPhoneTransportationServiceOutputPort findByPhoneTransportationServiceOutputPort;
    private final FindTypesTransportationServiceOutputPort findTypesTransportationServiceOutputPort;
    private final FindAllRouteStepTransportationServiceOutputPort findAllRouteStepTransportationServiceOutputPort;
    private final FindByRouteStepsIdsTransportationServiceOutputPurt findByRouteStepsIdsTransportationServiceOutputPurt;
    private final FindAllRoutesByDepartureCityOutputPort findAllRoutesByDepartureCityOutputPort;

    public TransportationServiceOutputPortAggregateImpl(CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort,
                                                        RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort,
                                                        FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort,
                                                        FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort,
                                                        FindByPhoneTransportationServiceOutputPort findByPhoneTransportationServiceOutputPort,
                                                        FindTypesTransportationServiceOutputPort findTypesTransportationServiceOutputPort, FindAllRouteStepTransportationServiceOutputPort findAllRouteStepTransportationServiceOutputPort, FindByRouteStepsIdsTransportationServiceOutputPurt findByRouteStepsIdsTransportationServiceOutputPurt, FindAllRoutesByDepartureCityOutputPort findAllRoutesByDepartureCityOutputPort) {
        this.createBookingTransportationServiceOutputPort = createBookingTransportationServiceOutputPort;
        this.revokeBookingTransportationServiceOutputPort = revokeBookingTransportationServiceOutputPort;
        this.findByParamsTransportationServiceOutputPort = findByParamsTransportationServiceOutputPort;
        this.findAllTransportationServiceOutputPort = findAllTransportationServiceOutputPort;
        this.findByPhoneTransportationServiceOutputPort = findByPhoneTransportationServiceOutputPort;
        this.findTypesTransportationServiceOutputPort = findTypesTransportationServiceOutputPort;
        this.findAllRouteStepTransportationServiceOutputPort = findAllRouteStepTransportationServiceOutputPort;
        this.findByRouteStepsIdsTransportationServiceOutputPurt = findByRouteStepsIdsTransportationServiceOutputPurt;
        this.findAllRoutesByDepartureCityOutputPort = findAllRoutesByDepartureCityOutputPort;
    }


    public FindByRouteStepsIdsTransportationServiceOutputPurt getFindByRouteStepsIdsTransportationServiceOutputPurt() {
        return findByRouteStepsIdsTransportationServiceOutputPurt;
    }

    @Override
    public FindAllRoutesByDepartureCityOutputPort getFindAllRoutesByDepartureCityOutputPort() {
        return findAllRoutesByDepartureCityOutputPort;
    }

    public CreateBookingTransportationServiceOutputPort getCreateBookingTransportationServiceOutputPort() {
        return createBookingTransportationServiceOutputPort;
    }

    public RevokeBookingTransportationServiceOutputPort getRevokeBookingTransportationServiceOutputPort() {
        return revokeBookingTransportationServiceOutputPort;
    }

    public FindByParamsTransportationServiceOutputPort getFindByParamsTransportationServiceOutputPort() {
        return findByParamsTransportationServiceOutputPort;
    }

    public FindAllTransportationServiceOutputPort getFindAllTransportationServiceOutputPort() {
        return findAllTransportationServiceOutputPort;
    }

    @Override
    public FindByPhoneTransportationServiceOutputPort getFindByPhoneTransportationServiceOutputPort() {
        return findByPhoneTransportationServiceOutputPort;
    }

    @Override
    public FindTypesTransportationServiceOutputPort getFindTypesTransportationServiceOutputPort() {
        return findTypesTransportationServiceOutputPort;
    }

    public FindAllRouteStepTransportationServiceOutputPort getFindAllRouteStepTransportationServiceOutputPort() {
        return findAllRouteStepTransportationServiceOutputPort;
    }
}
