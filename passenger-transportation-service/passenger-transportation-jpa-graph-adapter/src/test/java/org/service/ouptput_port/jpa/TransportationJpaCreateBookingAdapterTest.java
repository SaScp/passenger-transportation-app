package org.service.ouptput_port.jpa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.service.entity.BookingParamsEntity;

import org.service.output_port.exception.RouteIsNullException;
import org.service.output_port.jpa.TransportationJpaCreateBookingAdapter;
import org.service.output_port.model.Booking;
import org.service.output_port.model.Location;
import org.service.output_port.model.Route;
import org.service.output_port.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



@Transactional
@DataJpaTest
@ExtendWith(SpringExtension.class)
class TransportationJpaCreateBookingAdapterTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TransportationJpaCreateBookingAdapter adapter;

    @Autowired
    private TestEntityManager testEntityManager;


    private Route createAndPersistRoute(LocalDateTime departureTime, String id) {

        Location cityA = testEntityManager.persistAndFlush(new Location("loc1" + id, "CityA"));
        Location cityB = testEntityManager.persistAndFlush(new Location("loc2" + id, "CityB"));
        Route route = new Route(
                id,
                cityA,
                cityB,
                departureTime,
                departureTime.plusHours(1),
                new ArrayList<>()
        );
        return testEntityManager.persistAndFlush(route);
    }



    @Test
    void createBookingSuccessfully() {
        // Arrange
        createAndPersistRoute(LocalDateTime.now(), "route-1");
        BookingParamsEntity entity = new BookingParamsEntity("+1234567890", "route-1");

        // Act
        adapter.create(entity);


        Optional<Booking> savedBooking = bookingRepository.findAll().stream().findFirst();

        assertTrue(savedBooking.isPresent());
        assertEquals("+1234567890", savedBooking.get().getUserPhone().getNumberPhone());
        assertEquals("route-1", savedBooking.get().getRoute());
    }

    @Test
    void createMultipleBookings() {
        createAndPersistRoute(LocalDateTime.now(), "route-2");
        createAndPersistRoute(LocalDateTime.now(), "route-3");
        BookingParamsEntity entity1 = new BookingParamsEntity("+1234567890", "route-2");
        BookingParamsEntity entity2 = new BookingParamsEntity("+1234567890", "route-3");

        adapter.create(entity1);
        adapter.create(entity2);


        List<Booking> bookings = bookingRepository.findAll();
        assertEquals(2, bookings.size());
    }

    @Test
    void createBookingWithNullPhoneThrowsException() {
        BookingParamsEntity entity = new BookingParamsEntity(null, "route-1");

        assertThrows(Exception.class, () -> adapter.create(entity));
    }

    @Test
    void createBookingWithNullRouteThrowsException() {
        BookingParamsEntity entity = new BookingParamsEntity("+1234567890", null);


        assertThrows(RouteIsNullException.class, () -> adapter.create(entity));
    }
}