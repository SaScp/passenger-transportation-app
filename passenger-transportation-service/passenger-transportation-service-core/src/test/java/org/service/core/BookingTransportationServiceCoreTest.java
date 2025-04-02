package org.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingTransportationServiceCoreTest {

    @Mock
    private TransportationServiceOutputPortAggregate aggregate;

    @Mock
    private CreateBookingTransportationServiceOutputPort createBookingOutputPort;

    @Mock
    private FindByPhoneTransportationServiceOutputPort findByPhoneOutputPort;

    @Mock
    private RevokeBookingTransportationServiceOutputPort revokeBookingOutputPort;

    private BookingTransportationServiceCore bookingCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(aggregate.getOutputPort(CreateBookingTransportationServiceOutputPort.class))
                .thenReturn(createBookingOutputPort);
        when(aggregate.getOutputPort(FindByPhoneTransportationServiceOutputPort.class))
                .thenReturn(findByPhoneOutputPort);
        when(aggregate.getOutputPort(RevokeBookingTransportationServiceOutputPort.class))
                .thenReturn(revokeBookingOutputPort);
        bookingCore = new BookingTransportationServiceCore(aggregate);
    }

    @Test
    void testCreateBookingNormalization() {
        String rawPhone = "+7 (123) 456-78-90";
        BookingParamsEntity bookingParams = new BookingParamsEntity(rawPhone, "route1");

        bookingCore.createBooking(bookingParams);

        ArgumentCaptor<BookingParamsEntity> captor = ArgumentCaptor.forClass(BookingParamsEntity.class);
        verify(createBookingOutputPort, times(1)).create(captor.capture());
        BookingParamsEntity captured = captor.getValue();

        assertEquals("7(123)456-78-90", captured.numberPhone());
        assertEquals("route1", captured.routeId());
    }

    @Test
    void testCreateBookingInvalidPhone() {
        BookingParamsEntity bookingParams = new BookingParamsEntity("12345", "route1");
        assertThrows(IsNotPhoneException.class, () -> bookingCore.createBooking(bookingParams));
        verify(createBookingOutputPort, never()).create(any());
    }

    @Test
    void testCreateBookingProblemDetailsException() {
        // Симулируем выброс ProblemDetailsException из output-порта
        BookingParamsEntity bookingParams = new BookingParamsEntity("+7 (123) 456-78-90", "route1");
        doThrow(new ProblemDetailsException(500, "err")).when(createBookingOutputPort).create(any());
        assertThrows(ProblemDetailsException.class, () -> bookingCore.createBooking(bookingParams));
    }

    @Test
    void testRevokeBooking() {
        String bookingId = "booking123";
        doNothing().when(revokeBookingOutputPort).revoke(bookingId);

        bookingCore.revokeBooking(bookingId);
        verify(revokeBookingOutputPort, times(1)).revoke(bookingId);
    }

    @Test
    void testRevokeBookingThrowsException() {
        String bookingId = "bookingFail";
        doThrow(new RuntimeException("Revoke failed")).when(revokeBookingOutputPort).revoke(bookingId);
        assertThrows(RuntimeException.class, () -> bookingCore.revokeBooking(bookingId));
    }

    @Test
    void testFindByPhoneNormalization() {
        String rawPhone = "+7 (123)456-78-90";
        String normalizedPhone = "7(123)456-78-90";
        PageEntity page = new PageEntity(1, 10);
        List<BookingEntity> expectedBookings = Collections.singletonList(mock(BookingEntity.class));
        when(findByPhoneOutputPort.findBy(normalizedPhone, page)).thenReturn(expectedBookings);

        List<BookingEntity> result = bookingCore.findByPhone(rawPhone, page);
        assertEquals(expectedBookings, result);
        verify(findByPhoneOutputPort, times(1)).findBy(normalizedPhone, page);
    }

    @Test
    void testFindByPhoneInvalid() {
        String invalidPhone = "12345";
        PageEntity page = new PageEntity(1, 10);
        assertThrows(IsNotPhoneException.class, () -> bookingCore.findByPhone(invalidPhone, page));
        verify(findByPhoneOutputPort, never()).findBy(anyString(), any());
    }

    @Test
    void testFindByPhoneWithNullPage() {
        // Проверяем, что передача null для PageEntity корректно передается в output-порт (если такое допустимо)
        String rawPhone = "+7 (123)456-78-90";
        String normalizedPhone = "7(123)456-78-90";
        List<BookingEntity> expectedBookings = Collections.singletonList(mock(BookingEntity.class));
        when(findByPhoneOutputPort.findBy(normalizedPhone, null)).thenReturn(expectedBookings);

        List<BookingEntity> result = bookingCore.findByPhone(rawPhone, null);
        assertEquals(expectedBookings, result);
        verify(findByPhoneOutputPort, times(1)).findBy(normalizedPhone, null);
    }
}
