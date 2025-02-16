package org.service.output_port;


public class TransportationServiceOutputPortAggregateImpl implements TransportationServiceOutputPortAggregate {
    private final CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort;
    private final RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort;
    private final FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort;
    private final FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort;
    private final FindByPhoneTransportationServiceOutputPort findByPhoneTransportationServiceOutputPort;

    public TransportationServiceOutputPortAggregateImpl(CreateBookingTransportationServiceOutputPort createBookingTransportationServiceOutputPort,
                                                        RevokeBookingTransportationServiceOutputPort revokeBookingTransportationServiceOutputPort,
                                                        FindByParamsTransportationServiceOutputPort findByParamsTransportationServiceOutputPort,
                                                        FindAllTransportationServiceOutputPort findAllTransportationServiceOutputPort, FindByPhoneTransportationServiceOutputPort findByPhoneTransportationServiceOutputPort) {
        this.createBookingTransportationServiceOutputPort = createBookingTransportationServiceOutputPort;
        this.revokeBookingTransportationServiceOutputPort = revokeBookingTransportationServiceOutputPort;
        this.findByParamsTransportationServiceOutputPort = findByParamsTransportationServiceOutputPort;
        this.findAllTransportationServiceOutputPort = findAllTransportationServiceOutputPort;
        this.findByPhoneTransportationServiceOutputPort = findByPhoneTransportationServiceOutputPort;
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
}
