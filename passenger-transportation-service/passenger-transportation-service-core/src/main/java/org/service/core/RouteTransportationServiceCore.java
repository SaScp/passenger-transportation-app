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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class RouteTransportationServiceCore extends TransportationServiceCore implements RouteTransportationServiceInputPort {

    public RouteTransportationServiceCore(TransportationServiceOutputPortAggregate aggregate, ExecutorService executorService) {
        super(aggregate, executorService);
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        return CompletableFuture.supplyAsync(() ->
                aggregate.getOutputPort(FindByParamsTransportationServiceOutputPort.class).findBy(entity, pageEntity), executorService);
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findAll(PageEntity pageEntity) {
        return CompletableFuture.supplyAsync(() -> aggregate.getOutputPort(FindAllTransportationServiceOutputPort.class).findAll(pageEntity), executorService);
    }

    @Override
    public CompletableFuture<List<TypeEntity>> findAllType() {
        return CompletableFuture.supplyAsync(() -> aggregate.getOutputPort(FindTypesTransportationServiceOutputPort.class).findAllTypeEntity(), executorService);
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findRoutesByDepId(String id, PageEntity pageEntity) {
        return CompletableFuture.supplyAsync(() -> {
            return aggregate.getOutputPort(FindAllRoutesByDepartureCityOutputPort.class).findAllByDepartureCity(id, pageEntity);
        }, executorService);
    }
}
