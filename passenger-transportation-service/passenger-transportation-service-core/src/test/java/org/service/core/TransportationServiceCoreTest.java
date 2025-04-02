package org.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.service.entity.*;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.FindAllRoutesByDepartureCityOutputPort;
import org.service.output_port.find.FindAllRouteStepTransportationServiceOutputPort;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.find.FindByRouteStepsIdsTransportationServiceOutputPort;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportationServiceCoreTest {

    @Mock
    private TransportationServiceOutputPortAggregate aggregate;

    @Mock
    private CreateBookingTransportationServiceOutputPort createBookingOutputPort;

    @Mock
    private FindByParamsTransportationServiceOutputPort findByParamsOutputPort;

    @Mock
    private FindAllRouteStepTransportationServiceOutputPort findAllRouteStepOutputPort;

    @Mock
    private FindAllTransportationServiceOutputPort findAllOutputPort;

    @Mock
    private RevokeBookingTransportationServiceOutputPort revokeBookingOutputPort;

    @Mock
    private FindByPhoneTransportationServiceOutputPort findByPhoneOutputPort;

    @Mock
    private FindTypesTransportationServiceOutputPort findTypesOutputPort;

    @Mock
    private FindByRouteStepsIdsTransportationServiceOutputPort findByRouteStepsIdsOutputPort;

    @Mock
    private FindAllRoutesByDepartureCityOutputPort findAllRoutesByDepartureCityOutputPort;

    private TransportationServiceCore transportationServiceCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(aggregate.getOutputPort(CreateBookingTransportationServiceOutputPort.class)).thenReturn(createBookingOutputPort);
        when(aggregate.getOutputPort(FindByParamsTransportationServiceOutputPort.class)).thenReturn(findByParamsOutputPort);
        when(aggregate.getOutputPort(FindAllRouteStepTransportationServiceOutputPort.class)).thenReturn(findAllRouteStepOutputPort);
        when(aggregate.getOutputPort(FindAllTransportationServiceOutputPort.class)).thenReturn(findAllOutputPort);
        when(aggregate.getOutputPort(RevokeBookingTransportationServiceOutputPort.class)).thenReturn(revokeBookingOutputPort);
        when(aggregate.getOutputPort(FindByPhoneTransportationServiceOutputPort.class)).thenReturn(findByPhoneOutputPort);
        when(aggregate.getOutputPort(FindTypesTransportationServiceOutputPort.class)).thenReturn(findTypesOutputPort);
        when(aggregate.getOutputPort(FindByRouteStepsIdsTransportationServiceOutputPort.class)).thenReturn(findByRouteStepsIdsOutputPort);
        when(aggregate.getOutputPort(FindAllRoutesByDepartureCityOutputPort.class)).thenReturn(findAllRoutesByDepartureCityOutputPort);

        transportationServiceCore = new TransportationServiceCore(aggregate);
    }

    @Test
    void testFindByParams() {
        ParamsEntity params = new ParamsEntity(null, "bus", "CityA", "CityB");
        PageEntity page = new PageEntity(1, 10);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));

        when(findByParamsOutputPort.findBy(params, page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = transportationServiceCore.findByParams(params, page);
        assertEquals(expectedRoutes, result);
        verify(findByParamsOutputPort, times(1)).findBy(params, page);
    }

    @Test
    void testFindAllGraphEmpty() {
        when(findAllRouteStepOutputPort.findAll()).thenReturn(Collections.emptyList());
        GraphEntity graph = transportationServiceCore.findAll();

        assertNotNull(graph);
        assertTrue(graph.nodes().isEmpty());
        assertTrue(graph.edges().isEmpty());
    }

    @Test
    void testFindAllWithPage() {
        PageEntity page = new PageEntity(2, 5);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));
        when(findAllOutputPort.findAll(page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = transportationServiceCore.findAll(page);
        assertEquals(expectedRoutes, result);
        verify(findAllOutputPort, times(1)).findAll(page);
    }

    @Test
    void testCreateBookingNormalization() {
        String rawPhone = "+7 (123) 456-78-90";
        BookingParamsEntity bookingParams = new BookingParamsEntity(rawPhone, "route1");

        transportationServiceCore.createBooking(bookingParams);

        ArgumentCaptor<BookingParamsEntity> captor = ArgumentCaptor.forClass(BookingParamsEntity.class);
        verify(createBookingOutputPort, times(1)).create(captor.capture());
        BookingParamsEntity captured = captor.getValue();

        assertEquals("7(123)456-78-90", captured.numberPhone());
        assertEquals("route1", captured.routeId());
    }

    @Test
    void testCreateBookingInvalidPhone() {
        BookingParamsEntity bookingParams = new BookingParamsEntity("12345", "route1");
        assertThrows(IsNotPhoneException.class, () -> transportationServiceCore.createBooking(bookingParams));
        verify(createBookingOutputPort, never()).create(any());
    }


    @Test
    void testRevokeBooking() {
        String bookingId = "booking123";
        doNothing().when(revokeBookingOutputPort).revoke(bookingId);
        transportationServiceCore.revokeBooking(bookingId);
        verify(revokeBookingOutputPort, times(1)).revoke(bookingId);
    }

    @Test
    void testFindByPhoneNormalization() {
        String rawPhone = "+7 (123)456-78-90";
        String normalizedPhone = "7(123)456-78-90";
        PageEntity page = new PageEntity(1, 10);
        List<BookingEntity> expectedBookings = Collections.singletonList(mock(BookingEntity.class));
        when(findByPhoneOutputPort.findBy(normalizedPhone, page)).thenReturn(expectedBookings);

        List<BookingEntity> result = transportationServiceCore.findByPhone(rawPhone, page);
        assertEquals(expectedBookings, result);
        verify(findByPhoneOutputPort, times(1)).findBy(normalizedPhone, page);
    }

    // Тест для findByPhone с неверным форматом номера
    @Test
    void testFindByPhoneInvalid() {
        String invalidPhone = "12345";
        PageEntity page = new PageEntity(1, 10);
        assertThrows(IsNotPhoneException.class, () -> transportationServiceCore.findByPhone(invalidPhone, page));
        verify(findByPhoneOutputPort, never()).findBy(anyString(), any());
    }

    @Test
    void testFindAllType() {
        List<TypeEntity> expectedTypes = Collections.singletonList(mock(TypeEntity.class));
        when(findTypesOutputPort.findAllTypeEntity()).thenReturn(expectedTypes);

        List<TypeEntity> result = transportationServiceCore.findAllType();
        assertEquals(expectedTypes, result);
        verify(findTypesOutputPort, times(1)).findAllTypeEntity();
    }

    @Test
    void testFindGraphByIdsEmpty() {
        when(findByRouteStepsIdsOutputPort.findRouteStepsByIds(Collections.emptyList())).thenReturn(Collections.emptyList());
        GraphEntity graph = transportationServiceCore.findGraphByIds(Collections.emptyList());

        assertNotNull(graph);
        assertTrue(graph.nodes().isEmpty());
        assertTrue(graph.edges().isEmpty());
    }


    @Test
    void testFindRoutesByDepId() {
        String departureId = "CityA";
        PageEntity page = new PageEntity(1, 10);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));
        when(findAllRoutesByDepartureCityOutputPort.findAllByDepartureCity(departureId, page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = transportationServiceCore.findRoutesByDepId(departureId, page);
        assertEquals(expectedRoutes, result);
        verify(findAllRoutesByDepartureCityOutputPort, times(1)).findAllByDepartureCity(departureId, page);
    }
}
