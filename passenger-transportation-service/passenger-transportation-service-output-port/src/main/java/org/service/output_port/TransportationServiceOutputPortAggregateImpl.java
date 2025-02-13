package org.service.output_port;

public class TransportationServiceOutputPortAggregateImpl implements TransportationServiceOutputPortAggregate {
    private final CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort;
    private final RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort;
    private final FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort;
    private final FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort;

    public TransportationServiceOutputPortAggregateImpl(CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort,
                                                        RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort,
                                                        FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort,
                                                        FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort) {
        this.createBookingTransportationServiceOutputPort = createBookingTransportationServiceOutputPort;
        this.revokeBookingTransportationServiceOutputPort = revokeBookingTransportationServiceOutputPort;
        this.findByParamsTransportationServiceOutputPort = findByParamsTransportationServiceOutputPort;
        this.findAllTransportationServiceOutputPort = findAllTransportationServiceOutputPort;
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
}
