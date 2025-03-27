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

public class TransportationServiceCore implements TransportationServiceInputPort {

   private final TransportationServiceOutputPortAggregate aggregate;

    private static final String PHONE_PATTERN = "^(?:\\+7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$";

    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity) {

        return aggregate.getOutputPort(FindByParamsTransportationServiceOutputPort.class).findBy(entity, pageEntity);
    }


    @Override
    public GraphEntity findAll() {
        List<EdgeEntity> routeSteps = aggregate.getOutputPort(FindAllRouteStepTransportationServiceOutputPort.class).findAll();
        Set<Map<String, String>> nodes = EdgeResponseFactory.createResponsePart(routeSteps, Type.NODE);
        Set<Map<String, String>> edges = EdgeResponseFactory.createResponsePart(routeSteps, Type.EDGE);
        return new GraphEntity(nodes, edges);
    }

    @Override
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return aggregate.getOutputPort(FindAllTransportationServiceOutputPort.class).findAll(pageEntity);
    }

    @Override
    public void createBooking(BookingParamsEntity bookingParams) {
        try {
            if (isPhone(bookingParams.numberPhone())) {
                aggregate.getOutputPort(CreateBookingTransportationServiceOutputPort.class).create(bookingParams);
            } else {
                throw new IsNotPhoneException();
            }
        } catch (IsNotPhoneException e) {
            throw new IsNotPhoneException();
        }
        catch (ProblemDetailsException e) {
            throw new ProblemDetailsException(e);
        }
    }

    @Override
    public void revokeBooking(String id) {
         aggregate.getOutputPort(RevokeBookingTransportationServiceOutputPort.class).revoke(id);
    }

    @Override
    public List<BookingEntity> findByPhone(String phone, PageEntity pageEntity) {
        if (isPhone(phone)) {
            return aggregate.getOutputPort(FindByPhoneTransportationServiceOutputPort.class).findBy(phone, pageEntity);
        } else {
            throw new IsNotPhoneException();
        }
    }

    @Override
    public List<TypeEntity> findAllType() {
        return aggregate.getOutputPort(FindTypesTransportationServiceOutputPort.class).findAllTypeEntity();
    }


    public GraphEntity findGraphByIds(List<String> ids) {
        List<RouteStepEntity> routeStepsByIds = aggregate.getOutputPort(FindByRouteStepsIdsTransportationServiceOutputPort.class).findRouteStepsByIds(ids);
        Set<Map<String, String>> nodes = RouteStepResponseFactory.createResponsePart(routeStepsByIds, Type.NODE);
        Set<Map<String, String>> edges = RouteStepResponseFactory.createResponsePart(routeStepsByIds, Type.EDGE);
        return new GraphEntity(nodes, edges);
    }

    @Override
    public List<RoutesEntity> findRoutesByDepId(String id, PageEntity pageEntity) {
        return aggregate.getOutputPort(FindAllRoutesByDepartureCityOutputPort.class).findAllByDepartureCity(id, pageEntity);
    }

    private boolean isPhone(String phone) {
        return Pattern.compile(PHONE_PATTERN).matcher(phone).find();
    }
}
