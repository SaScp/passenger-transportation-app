package org.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.entity.TypeEntity;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteTransportationServiceCoreTest {

    @Mock
    private TransportationServiceOutputPortAggregate aggregate;

    @Mock
    private FindByParamsTransportationServiceOutputPort findByParamsOutputPort;

    @Mock
    private FindAllTransportationServiceOutputPort findAllOutputPort;

    @Mock
    private FindTypesTransportationServiceOutputPort findTypesOutputPort;

    @Mock
    private FindAllRoutesByDepartureCityOutputPort findAllRoutesByDepartureCityOutputPort;

    private RouteTransportationServiceCore routeCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(aggregate.getOutputPort(FindByParamsTransportationServiceOutputPort.class))
                .thenReturn(findByParamsOutputPort);
        when(aggregate.getOutputPort(FindAllTransportationServiceOutputPort.class))
                .thenReturn(findAllOutputPort);
        when(aggregate.getOutputPort(FindTypesTransportationServiceOutputPort.class))
                .thenReturn(findTypesOutputPort);
        when(aggregate.getOutputPort(FindAllRoutesByDepartureCityOutputPort.class))
                .thenReturn(findAllRoutesByDepartureCityOutputPort);
        routeCore = new RouteTransportationServiceCore(aggregate);
    }

    @Test
    void testFindByParams() {
        ParamsEntity params = new ParamsEntity(null, "bus", "CityA", "CityB");
        PageEntity page = new PageEntity(1, 10);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));
        when(findByParamsOutputPort.findBy(params, page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = routeCore.findByParams(params, page);
        assertEquals(expectedRoutes, result);
        verify(findByParamsOutputPort, times(1)).findBy(params, page);
    }

    @Test
    void testFindByParamsEmpty() {
        ParamsEntity params = new ParamsEntity(null, "train", "CityX", "CityY");
        PageEntity page = new PageEntity(1, 5);
        when(findByParamsOutputPort.findBy(params, page)).thenReturn(Collections.emptyList());

        List<RoutesEntity> result = routeCore.findByParams(params, page);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllWithPage() {
        PageEntity page = new PageEntity(2, 5);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));
        when(findAllOutputPort.findAll(page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = routeCore.findAll(page);
        assertEquals(expectedRoutes, result);
        verify(findAllOutputPort, times(1)).findAll(page);
    }

    @Test
    void testFindAllEmpty() {
        PageEntity page = new PageEntity(1, 10);
        when(findAllOutputPort.findAll(page)).thenReturn(Collections.emptyList());

        List<RoutesEntity> result = routeCore.findAll(page);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllType() {
        List<TypeEntity> expectedTypes = Collections.singletonList(mock(TypeEntity.class));
        when(findTypesOutputPort.findAllTypeEntity()).thenReturn(expectedTypes);

        List<TypeEntity> result = routeCore.findAllType();
        assertEquals(expectedTypes, result);
        verify(findTypesOutputPort, times(1)).findAllTypeEntity();
    }

    @Test
    void testFindAllTypeEmpty() {
        when(findTypesOutputPort.findAllTypeEntity()).thenReturn(Collections.emptyList());

        List<TypeEntity> result = routeCore.findAllType();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindRoutesByDepId() {
        String departureId = "CityA";
        PageEntity page = new PageEntity(1, 10);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));
        when(findAllRoutesByDepartureCityOutputPort.findAllByDepartureCity(departureId, page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = routeCore.findRoutesByDepId(departureId, page);
        assertEquals(expectedRoutes, result);
        verify(findAllRoutesByDepartureCityOutputPort, times(1)).findAllByDepartureCity(departureId, page);
    }

    @Test
    void testFindRoutesByDepIdEmpty() {
        String departureId = "CityZ";
        PageEntity page = new PageEntity(1, 10);
        when(findAllRoutesByDepartureCityOutputPort.findAllByDepartureCity(departureId, page))
                .thenReturn(Collections.emptyList());

        List<RoutesEntity> result = routeCore.findRoutesByDepId(departureId, page);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
