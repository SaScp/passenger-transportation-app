package org.service.input_port;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.entity.TypeEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RouteTransportationServiceInputPort {

    CompletableFuture<List<RoutesEntity>> findByParams(ParamsEntity entity, PageEntity pageEntity);

    CompletableFuture<List<RoutesEntity>> findAll(PageEntity pageEntity);

    CompletableFuture<List<TypeEntity>> findAllType();

    CompletableFuture<List<RoutesEntity>> findRoutesByDepId(String id, PageEntity pageEntity);
}
