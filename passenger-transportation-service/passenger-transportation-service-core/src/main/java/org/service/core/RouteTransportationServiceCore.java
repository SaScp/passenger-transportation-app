package org.service.core;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.entity.TypeEntity;
import org.service.input_port.RouteTransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;

import java.util.List;

public class RouteTransportationServiceCore extends TransportationServiceCore implements RouteTransportationServiceInputPort {

    public RouteTransportationServiceCore(TransportationServiceOutputPortAggregate aggregate) {
        super(aggregate);
    }

    @Override
    public List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        return aggregate.getOutputPort(FindByParamsTransportationServiceOutputPort.class).findBy(entity, pageEntity);
    }

    @Override
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return aggregate.getOutputPort(FindAllTransportationServiceOutputPort.class).findAll(pageEntity);
    }

    @Override
    public List<TypeEntity> findAllType() {
        return aggregate.getOutputPort(FindTypesTransportationServiceOutputPort.class).findAllTypeEntity();
    }

    @Override
    public List<RoutesEntity> findRoutesByDepId(String id, PageEntity pageEntity) {
        return aggregate.getOutputPort(FindAllRoutesByDepartureCityOutputPort.class).findAllByDepartureCity(id, pageEntity);
    }
}
