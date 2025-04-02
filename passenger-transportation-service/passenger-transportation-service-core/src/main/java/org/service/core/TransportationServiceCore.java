package org.service.core;

import org.service.core.factory.edge.EdgeResponseFactory;
import org.service.core.factory.Type;
import org.service.core.factory.route_step.RouteStepResponseFactory;
import org.service.entity.*;
import org.service.exception.ProblemDetailsException;
import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.*;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class TransportationServiceCore {

   protected final TransportationServiceOutputPortAggregate aggregate;

    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        this.aggregate = aggregate;
    }
}
