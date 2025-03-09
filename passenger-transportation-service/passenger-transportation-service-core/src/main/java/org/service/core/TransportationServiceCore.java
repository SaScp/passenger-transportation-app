package org.service.core;

import org.service.core.factory.ResponseFactory;
import org.service.core.factory.Type;
import org.service.entity.*;
import org.service.exception.ProblemDetailsException;
import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.entity.RouteStepEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class TransportationServiceCore implements TransportationServiceInputPort {

   private final TransportationServiceOutputPortAggregate aggregate;


    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        return aggregate.getFindByParamsTransportationServiceOutputPort().findBy(entity, pageEntity);
    }

    @Override
    public GraphEntity findAll() {
        List<RouteStepEntity> routeSteps = aggregate.getFindAllRouteStepTransportationServiceOutputPort().findAll();
        Set<Map<String, String>> nodes = ResponseFactory.createResponsePart(routeSteps, Type.NODE);
        Set<Map<String, String>> edges = ResponseFactory.createResponsePart(routeSteps, Type.EDGE);
        return new GraphEntity(nodes, edges);
    }

    @Override
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return aggregate.getFindAllTransportationServiceOutputPort().findAll(pageEntity);
    }

    @Override
    public void createBooking(BookingParamsEntity bookingParams) {
        try {
            if (Pattern.compile("^(?:\\+7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$").matcher(bookingParams.getNumberPhone()).find()) {
                aggregate.getCreateBookingTransportationServiceOutputPort().create(bookingParams);
            } else {
                throw new IsNotPhoneException();
            }
        } catch (ProblemDetailsException e) {
            throw new ProblemDetailsException(e);
        }
    }

    @Override
    public void revokeBooking(String id) {
         aggregate.getRevokeBookingTransportationServiceOutputPort().revoke(id);
    }

    @Override
    public List<BookingEntity> findByPhone(String phone, PageEntity pageEntity) {
        phone = phone.replace(" ", "+");
        return aggregate.getFindByPhoneTransportationServiceOutputPort().findBy(phone, pageEntity);
    }

    @Override
    public List<TypeEntity> findAllType() {
        return aggregate.getFindTypesTransportationServiceOutputPort().findAllTypeEntity();
    }


    public GraphEntity findGraphByIds(List<String> ids) {
        List<RouteStepEntity> routeStepsByIds = aggregate.getFindByRouteStepsIdsTransportationServiceOutputPurt().findRouteStepsByIds(ids);
        Set<Map<String, String>> nodes = ResponseFactory.createResponsePart(routeStepsByIds, Type.NODE);
        Set<Map<String, String>> edges = ResponseFactory.createResponsePart(routeStepsByIds, Type.EDGE);
        return new GraphEntity(nodes, edges);
    }

}
