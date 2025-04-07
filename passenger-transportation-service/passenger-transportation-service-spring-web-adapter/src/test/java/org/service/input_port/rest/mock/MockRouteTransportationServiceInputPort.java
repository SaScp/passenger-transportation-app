package org.service.input_port.rest.mock;


import org.service.entity.*;
import org.service.input_port.RouteTransportationServiceInputPort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A mock implementation of RouteTransportationServiceInputPort for testing purposes.
 * Provides predefined route and type data with basic filtering.
 */
public class MockRouteTransportationServiceInputPort implements RouteTransportationServiceInputPort {

    private final List<RoutesEntity> allRoutes;
    private final List<TypeEntity> allTypes;

    public MockRouteTransportationServiceInputPort() {
        LocationEntity mockCityA = new LocationEntity("mockA", "Mock City A");
        LocationEntity mockCityB = new LocationEntity("mockB", "Mock City B");
        LocationEntity mockCityC = new LocationEntity("mockC", "Mock City C");

        allRoutes = Arrays.asList(
                new RoutesEntity("mockRoute1", mockCityA, mockCityB, "09:00", "11:00", "BUS", 45.0, Collections.emptyList()),
                new RoutesEntity("mockRoute2", mockCityB, mockCityC, "12:00", "13:30", "TRAIN", 70.0, Collections.emptyList()),
                new RoutesEntity("mockRoute3", mockCityA, mockCityC, "10:00", "14:00", "BUS", 90.0, Collections.emptyList()),
                new RoutesEntity("mockRoute4", mockCityA, mockCityB, "15:00", "17:00", "BUS", 48.0, Collections.emptyList()) // Another BUS route A->B
        );

        allTypes = Arrays.asList(
                new TypeEntity(1L, "BUS"),
                new TypeEntity(2L, "TRAIN"),
                new TypeEntity(3L, "PLANE")
        );
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findByParams(ParamsEntity entity, PageEntity pageEntity) {
        System.out.println("Mock: Finding routes by params: " + entity + " with page " + pageEntity);

        Stream<RoutesEntity> stream = allRoutes.stream();


        if (entity.routeId() != null && !entity.routeId().isEmpty()) {
            stream = stream.filter(route -> entity.routeId().contains(route.id()));
        } else {
            if (entity.from() != null && !entity.from().isEmpty()) {
                stream = stream.filter(route -> entity.from().equals(route.departureCity().id()));
            }
            if (entity.to() != null && !entity.to().isEmpty()) {
                stream = stream.filter(route -> entity.to().equals(route.arrivalCity().id()));
            }
            if (entity.type() != null && !entity.type().isEmpty()) {
                stream = stream.filter(route -> entity.type().equalsIgnoreCase(route.type()));
            }

            if (entity.time() != null) {
                System.out.println("Mock: Time filtering is not implemented in this basic mock.");
            }
        }


        List<RoutesEntity> filteredAndPaged = stream
                .skip((long) pageEntity.pageNum() * pageEntity.pageSize())
                .limit(pageEntity.pageSize())
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(filteredAndPaged);
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findAll(PageEntity pageEntity) {
        System.out.println("Mock: Finding all routes with page " + pageEntity);

        List<RoutesEntity> pagedRoutes = allRoutes.stream()
                .skip((long) pageEntity.pageNum() * pageEntity.pageSize())
                .limit(pageEntity.pageSize())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(pagedRoutes);
    }

    @Override
    public CompletableFuture<List<TypeEntity>> findAllType() {
        System.out.println("Mock: Finding all types.");
        return CompletableFuture.completedFuture(allTypes);
    }

    @Override
    public CompletableFuture<List<RoutesEntity>> findRoutesByDepId(String id, PageEntity pageEntity) {
        System.out.println("Mock: Finding routes by departure id: " + id + " with page " + pageEntity);
        List<RoutesEntity> found = allRoutes.stream()
                .filter(route -> id.equals(route.departureCity().id()))
                .skip((long) pageEntity.pageNum() * pageEntity.pageSize())
                .limit(pageEntity.pageSize())
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(found);
    }
}
