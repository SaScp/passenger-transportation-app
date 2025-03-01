package org.service.ouptput_port.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.exception.ProblemDetailsException;
import org.service.ouptput_port.JpaTestConfiguration;
import org.service.ouptput_port.exception.RouteIsNullException;
import org.service.ouptput_port.model.Booking;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.model.Status;
import org.service.ouptput_port.repository.BookingRepository;
import org.service.ouptput_port.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
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



    @Test
    void createBookingSuccessfully() {
        // Arrange

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

        BookingParamsEntity entity1 = new BookingParamsEntity("+1234567890", "route-1");
        BookingParamsEntity entity2 = new BookingParamsEntity("+1234567890", "route-2");

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