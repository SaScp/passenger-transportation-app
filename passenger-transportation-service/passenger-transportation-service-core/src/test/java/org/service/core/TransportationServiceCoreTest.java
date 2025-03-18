package org.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.service.entity.*;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.*;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.time.LocalDateTime;
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
        ParamsEntity params = new ParamsEntity(LocalDateTime.now(), "bus", "CityA", "CityB");
        PageEntity page = new PageEntity(1, 10);
        List<RoutesEntity> expectedRoutes = Collections.singletonList(mock(RoutesEntity.class));

        when(findByParamsOutputPort.findBy(params, page)).thenReturn(expectedRoutes);

        List<RoutesEntity> result = transportationServiceCore.findByParams(params, page);
        assertEquals(expectedRoutes, result);
        verify(findByParamsOutputPort, times(1)).findBy(params, page);
    }

    @Test
    void testCreateBookingValidPhone() {
        BookingParamsEntity bookingParams = new BookingParamsEntity("+7 (123) 456-78-90", "route1");

        assertDoesNotThrow(() -> transportationServiceCore.createBooking(bookingParams));
        verify(createBookingOutputPort, times(1)).create(bookingParams);
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
}
