package org.service.input_port.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.input_port.BookingTransportationServiceInputPort;
import org.service.input_port.request.BookingQueryParam;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BookingRestControllerTest {

    private BookingTransportationServiceInputPort inputPort;
    private BookingRestController controller;

    @BeforeEach
    void setUp() {
        inputPort = mock(BookingTransportationServiceInputPort.class);
        controller = new BookingRestController(inputPort);
    }

    @Test
    void testBookingCreation() throws Exception {
        BookingQueryParam query = new BookingQueryParam("9876543210", "route-xyz");

        CompletableFuture<ResponseEntity<?>> futureResponse = controller.booking(query);
        ResponseEntity<?> responseEntity = futureResponse.get();

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(URI.create("/create"), responseEntity.getHeaders().getLocation());

        ArgumentCaptor<BookingParamsEntity> captor = ArgumentCaptor.forClass(BookingParamsEntity.class);
        verify(inputPort, times(1)).createBooking(captor.capture());
        BookingParamsEntity captured = captor.getValue();
        assertEquals("9876543210", captured.numberPhone());
        assertEquals("route-xyz", captured.routeId());
    }

    @Test
    void testMultipleBookingCreation() throws Exception {

        BookingQueryParam query1 = new BookingQueryParam("1112223333", "route-1");
        BookingQueryParam query2 = new BookingQueryParam("4445556666", "route-2");

        CompletableFuture<ResponseEntity<?>> futureResponse1 = controller.booking(query1);
        CompletableFuture<ResponseEntity<?>> futureResponse2 = controller.booking(query2);
        ResponseEntity<?> response1 = futureResponse1.get();
        ResponseEntity<?> response2 = futureResponse2.get();

        assertEquals(201, response1.getStatusCodeValue());
        assertEquals(201, response2.getStatusCodeValue());


        ArgumentCaptor<BookingParamsEntity> captor = ArgumentCaptor.forClass(BookingParamsEntity.class);
        verify(inputPort, times(2)).createBooking(captor.capture());
        List<BookingParamsEntity> capturedList = captor.getAllValues();
        assertEquals("1112223333", capturedList.get(0).numberPhone());
        assertEquals("route-1", capturedList.get(0).routeId());
        assertEquals("4445556666", capturedList.get(1).numberPhone());
        assertEquals("route-2", capturedList.get(1).routeId());
    }

    @Test
    void testRevokeBooking() throws Exception {
        doNothing().when(inputPort).revokeBooking("booking-1");

        controller.revokeBooking("booking-1");


        Thread.sleep(100);

        verify(inputPort, times(1)).revokeBooking("booking-1");
    }

    @Test
    void testRevokeBookingNonExistent() throws Exception {

        doNothing().when(inputPort).revokeBooking("non-existent-booking");

        controller.revokeBooking("non-existent-booking");

        Thread.sleep(100);
        verify(inputPort, times(1)).revokeBooking("non-existent-booking");
    }

    @Test
    void testFindTransportByPhone() throws Exception {
        String phone = "5551234567";
        PageEntity pageEntity = new PageEntity(0, 5);
        List<BookingEntity> expectedList = List.of(
                new BookingEntity("id-1", "2025-04-07T10:00:00", phone, "CONFIRMED", "route-001")
        );

        when(inputPort.findByPhone(eq(phone), any(PageEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedList));

        CompletableFuture<List<BookingEntity>> futureResult = controller.findTransportByPhone(phone, pageEntity);
        List<BookingEntity> result = futureResult.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(phone, result.get(0).userPhone());
        verify(inputPort, times(1)).findByPhone(eq(phone), eq(pageEntity));
    }

    @Test
    void testFindTransportByPhoneEmpty() throws Exception {
        String phone = "0000000000";
        PageEntity pageEntity = new PageEntity(0, 5);
        List<BookingEntity> emptyList = Collections.emptyList();

        when(inputPort.findByPhone(eq(phone), any(PageEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(emptyList));

        CompletableFuture<List<BookingEntity>> futureResult = controller.findTransportByPhone(phone, pageEntity);
        List<BookingEntity> result = futureResult.get();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(inputPort, times(1)).findByPhone(eq(phone), eq(pageEntity));
    }

    @Test
    void testFindTransportByPhoneInvalidPageParams() throws Exception {
        String phone = "5551234567";

        PageEntity invalidPageEntity = new PageEntity(-1, -5);
        List<BookingEntity> emptyList = Collections.emptyList();

        when(inputPort.findByPhone(eq(phone), eq(invalidPageEntity)))
                .thenReturn(CompletableFuture.completedFuture(emptyList));

        CompletableFuture<List<BookingEntity>> futureResult = controller.findTransportByPhone(phone, invalidPageEntity);
        List<BookingEntity> result = futureResult.get();


        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(inputPort, times(1)).findByPhone(eq(phone), eq(invalidPageEntity));
    }
}
