package org.service.input_port.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.service.entity.*;
import org.service.input_port.RouteTransportationServiceInputPort;
import org.service.input_port.request.FilterParamEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RouteRestControllerTest {

    private RouteTransportationServiceInputPort inputPort;
    private RouteRestController controller;

    @BeforeEach
    void setUp() {
        inputPort = mock(RouteTransportationServiceInputPort.class);
        controller = new RouteRestController(inputPort);
    }

    @Test
    void testFindTransportByFilter() throws Exception {
        // Подготовка параметров фильтрации
        LocalDateTime time = LocalDateTime.now();
        String type = "BUS";
        String from = "mockA";
        String to = "mockB";
        FilterParamEntity filterParam = new FilterParamEntity(time, type, from, to);
        PageEntity pageEntity = new PageEntity(0, 5);

        // Подготовка ожидаемых данных маршрута
        LocationEntity depCity = new LocationEntity("mockA", "Mock City A");
        LocationEntity arrCity = new LocationEntity("mockB", "Mock City B");
        RoutesEntity route = new RoutesEntity("mockRoute1", depCity, arrCity, "09:00", "11:00", "BUS", 45.0, Collections.emptyList());
        List<RoutesEntity> expectedRoutes = Collections.singletonList(route);

        // Конструируем ожидаемый ParamsEntity (предполагается, что конструктор ParamsEntity принимает time, type, from и to)
        ParamsEntity expectedParams = new ParamsEntity(time, type, from, to);

        when(inputPort.findByParams(eq(expectedParams), eq(pageEntity)))
                .thenReturn(CompletableFuture.completedFuture(expectedRoutes));

        CompletableFuture<List<RoutesEntity>> futureResult = controller.findTransport(pageEntity, filterParam);
        List<RoutesEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("mockRoute1", result.get(0).id());
        verify(inputPort, times(1)).findByParams(eq(expectedParams), eq(pageEntity));
    }

    @Test
    void testFindAllTransport() throws Exception {
        PageEntity pageEntity = new PageEntity(0, 5);

        // Подготовка списка маршрутов
        LocationEntity depCity = new LocationEntity("mockA", "Mock City A");
        LocationEntity arrCity = new LocationEntity("mockB", "Mock City B");
        RoutesEntity route1 = new RoutesEntity("mockRoute1", depCity, arrCity, "09:00", "11:00", "BUS", 45.0, Collections.emptyList());
        RoutesEntity route2 = new RoutesEntity("mockRoute2", new LocationEntity("mockB", "Mock City B"), new LocationEntity("mockC", "Mock City C"), "12:00", "13:30", "TRAIN", 70.0, Collections.emptyList());
        List<RoutesEntity> expectedRoutes = Arrays.asList(route1, route2);

        when(inputPort.findAll(eq(pageEntity)))
                .thenReturn(CompletableFuture.completedFuture(expectedRoutes));

        CompletableFuture<List<RoutesEntity>> futureResult = controller.findAllTransport(pageEntity);
        List<RoutesEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(inputPort, times(1)).findAll(eq(pageEntity));
    }

    @Test
    void testFindTransportById() throws Exception {
        PageEntity pageEntity = new PageEntity(0, 5);
        // Передаем список идентификаторов маршрутов
        List<String> routeIds = Arrays.asList("mockRoute1", "mockRoute3");

        // Ожидаемый ParamsEntity для поиска по id (предполагается, что конструктор ParamsEntity(List<String>) существует)
        ParamsEntity expectedParams = new ParamsEntity(routeIds);

        // Подготовка ожидаемых данных
        LocationEntity depCity = new LocationEntity("mockA", "Mock City A");
        LocationEntity arrCity = new LocationEntity("mockB", "Mock City B");
        RoutesEntity route = new RoutesEntity("mockRoute1", depCity, arrCity, "09:00", "11:00", "BUS", 45.0, Collections.emptyList());
        List<RoutesEntity> expectedRoutes = Collections.singletonList(route);

        when(inputPort.findByParams(eq(expectedParams), eq(pageEntity)))
                .thenReturn(CompletableFuture.completedFuture(expectedRoutes));

        CompletableFuture<List<RoutesEntity>> futureResult = controller.findTransportById(pageEntity, routeIds);
        List<RoutesEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("mockRoute1", result.get(0).id());
        verify(inputPort, times(1)).findByParams(eq(expectedParams), eq(pageEntity));
    }

    @Test
    void testFindTransportByDepId() throws Exception {
        PageEntity pageEntity = new PageEntity(0, 5);
        String depId = "mockA";

        // Подготовка ожидаемых маршрутов по id города отправления
        LocationEntity depCity = new LocationEntity("mockA", "Mock City A");
        LocationEntity arrCity = new LocationEntity("mockB", "Mock City B");
        RoutesEntity route = new RoutesEntity("mockRoute1", depCity, arrCity, "09:00", "11:00", "BUS", 45.0, Collections.emptyList());
        List<RoutesEntity> expectedRoutes = Collections.singletonList(route);

        when(inputPort.findRoutesByDepId(eq(depId), eq(pageEntity)))
                .thenReturn(CompletableFuture.completedFuture(expectedRoutes));

        CompletableFuture<List<RoutesEntity>> futureResult = controller.findTransportByDepId(pageEntity, depId);
        List<RoutesEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inputPort, times(1)).findRoutesByDepId(eq(depId), eq(pageEntity));
    }

    @Test
    void testFindTypes() throws Exception {
        // Подготовка ожидаемых типов
        TypeEntity type1 = new TypeEntity(1L, "BUS");
        TypeEntity type2 = new TypeEntity(2L, "TRAIN");
        TypeEntity type3 = new TypeEntity(3L, "PLANE");
        List<TypeEntity> expectedTypes = Arrays.asList(type1, type2, type3);

        when(inputPort.findAllType())
                .thenReturn(CompletableFuture.completedFuture(expectedTypes));

        CompletableFuture<List<TypeEntity>> futureResult = controller.findTypes();
        List<TypeEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("BUS", result.get(0).typeName());
        verify(inputPort, times(1)).findAllType();
    }
}
