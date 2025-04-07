package org.service.input_port.rest.mock;// Example package for mocks

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.input_port.BookingTransportationServiceInputPort;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A mock implementation of BookingTransportationServiceInputPort for testing purposes.
 * This implementation provides basic in-memory storage and retrieval logic.
 */
public class MockBookingTransportationServiceInputPort implements BookingTransportationServiceInputPort {

    private final ConcurrentHashMap<String, BookingEntity> bookings = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public MockBookingTransportationServiceInputPort() {
        bookings.put("booking-1", new BookingEntity("booking-1", "2025-04-07T10:00:00", "1234567890", "CONFIRMED", "route-abc"));
    }

    @Override
    public void createBooking(BookingParamsEntity bookingParams) {
        // Simulate creating a booking
        System.out.println("Mock: Creating booking for phone " + bookingParams.numberPhone() + " and route " + bookingParams.routeId());
        String id = "mock-booking-" + idCounter.incrementAndGet();
        BookingEntity newBooking = new BookingEntity(
                id,
                java.time.LocalDateTime.now().toString(), // Use current time as booking time
                bookingParams.numberPhone(),
                "CONFIRMED", // Default status
                bookingParams.routeId()
        );
        bookings.put(id, newBooking);
        // In a real mock, you might add more logic or store the created booking if needed for verification
    }

    @Override
    public void revokeBooking(String id) {

        System.out.println("Mock: Revoking booking with id " + id);
        bookings.remove(id);
    }

    @Override
    public CompletableFuture<List<BookingEntity>> findByPhone(String phone, PageEntity pageEntity) {
        // Simulate finding bookings by phone with simple filtering and pagination
        System.out.println("Mock: Finding bookings for phone " + phone + " with page " + pageEntity);
        List<BookingEntity> found = bookings.values().stream()
                .filter(booking -> phone.equals(booking.userPhone()))
                .skip((long) pageEntity.pageNum() * pageEntity.pageSize())
                .limit(pageEntity.pageSize())
                .toList(); // Requires Java 16+

        // Return a completed CompletableFuture with the result
        return CompletableFuture.completedFuture(found);
    }

    // Helper method for tests to clear state if needed
    public void clear() {
        bookings.clear();
        idCounter.set(0);
    }

    // Helper method for tests to get a booking
    public BookingEntity getBookingById(String id) {
        return bookings.get(id);
    }
}
