package org.service.output_port.cache;

import org.service.output_port.TransportationServiceOutputPort;

public abstract class CacheTransportationServiceOutputPort<T> implements TransportationServiceOutputPort {

    protected final T delegate;

    public CacheTransportationServiceOutputPort(T delegate) {
        this.delegate = delegate;
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return (Class<? extends TransportationServiceOutputPort>) delegate.getClass();
    }
}
