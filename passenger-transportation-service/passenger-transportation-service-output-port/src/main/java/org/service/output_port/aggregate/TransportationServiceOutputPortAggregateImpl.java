package org.service.output_port.aggregate;


import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.*;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.util.Map;
import java.util.Objects;

public class TransportationServiceOutputPortAggregateImpl implements TransportationServiceOutputPortAggregate {



    private final Map<Class<?>, TransportationServiceOutputPort> transportationOutputPortMap;

    public TransportationServiceOutputPortAggregateImpl(Map<Class<?>, TransportationServiceOutputPort> transportationOutputPortMap) {
        this.transportationOutputPortMap = transportationOutputPortMap;
    }


    @SuppressWarnings("unchecked")
    public <T> T getOutputPort(Class<T> type) {
        return (T) transportationOutputPortMap.get(type);
    }

}
