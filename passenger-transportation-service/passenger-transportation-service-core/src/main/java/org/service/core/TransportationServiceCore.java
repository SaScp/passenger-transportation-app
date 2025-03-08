package org.service.core;

import org.service.entity.*;
import org.service.exception.ProblemDetailsException;
import org.service.input_port.TransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.entity.RouteStepEntity;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class TransportationServiceCore implements TransportationServiceInputPort {

   private final TransportationServiceOutputPortAggregate aggregate;


    public TransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        System.out.println(aggregate.getFindByPhoneTransportationServiceOutputPort());
        System.out.println(aggregate.getFindByParamsTransportationServiceOutputPort());
        System.out.println(aggregate.getCreateBookingTransportationServiceOutputPort());
        System.out.println(aggregate.getRevokeBookingTransportationServiceOutputPort());
        System.out.println(aggregate.getFindAllTransportationServiceOutputPort());
        this.aggregate = aggregate;
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        return aggregate.getFindByParamsTransportationServiceOutputPort().findBy(entity, pageEntity);
    }

    @Override
    public GraphEntity findAll() {
        List<RouteStepEntity> all = aggregate.getFindAllRouteStepTransportationServiceOutputPort().findAll();
        return new GraphEntity(new HashSet<>(), new HashSet<>());
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
        return new GraphEntity(new HashSet<>(), new HashSet<>());
    }

    /*private Set<Map<String, String>> createNodes(List<RouteStep> routeSteps) {
        Set<Map<String, String>> result = new HashSet<>();

        int i = 0;
        int j = routeSteps.size() - 1;
        while (i <= j) {
            if (i == j) {
                result.add(createNode(routeSteps, i));
                result.add(createNode2(routeSteps, i));
            } else {
                result.add(createNode(routeSteps, i));
                result.add(createNode2(routeSteps, i));
                result.add(createNode(routeSteps, j));
                result.add(createNode2(routeSteps, j));
                j--;
            }
            i++;
        }


        return result;
    }


    private Set<Map<String, String>> createEdges(List<RouteStep> routeSteps) {
        Set<Map<String, String>> result = new HashSet<>(routeSteps.size());
        int i = 0;
        int j = routeSteps.size() - 1;
        while (i <= j) {
            if (i == j) {
                result.add(create(routeSteps, i));
            } else {
                result.add(create(routeSteps, i));
                result.add(create(routeSteps, j));
                j--;
            }
            i++;

        }

        return result;
    }

    private Map<String, String> createNode(List<RouteStepEntity> steps, int index) {
        return Map.of("id", steps.get(index).edgeId().fromLocationId(), "label", steps.get(index).edgeId().fromLocationId());
    }

    private Map<String, String> createNode2(List<RouteStep> steps, int index) {
        return Map.of("id", steps.get(index).getEdgeId().getToLocationId().getId(), "label", steps.get(index).getEdgeId().getToLocationId().getCName());
    }

    private Map<String, String> create(List<RouteStep> steps, int index) {
        return createEdge(steps.get(index).getRouteStep().toString(), steps.get(index).getEdgeId().getFromLocationId().getId(), steps.get(index).getEdgeId().getToLocationId().getId(), steps.get(index).getRouteId(), steps.get(index).getEdgeId().getCType().toString());
    }

    private Map<String, String> createEdge(String id, String from, String to, String routeId, String type) {
        return Map.of("id", String.format("%s-%s", routeId, id), "from", from, "to", to, "route_id", routeId, "type", type);
    }*/
}
