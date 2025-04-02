package org.service.ouptput_port.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.service.entity.BookingEntity;
import org.service.output_port.exception.BookingNotFoundException;
import org.service.output_port.exception.UserNotFoundException;
import org.service.output_port.jpa.TransportationJpaFindByPhoneAdapter;
import org.service.output_port.model.Booking;
import org.service.output_port.model.Status;
import org.service.output_port.model.User;
import org.service.output_port.repository.BookingRepository;
import org.service.output_port.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
public class TransportationJpaFindByPhoneAdapterTest {

    @Autowired
    private TransportationJpaFindByPhoneAdapter adapter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager testEntityManager;

    @Autowired
    private BookingRepository bookingRepository;


    @BeforeEach
    public void setUp() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        testEntityManager.clear();
    }

    @Test
    public void testUserNotFound() {
        String phone = "1234567890";

        assertThrows(UserNotFoundException.class, () -> adapter.findBy(phone, null));
    }

    @Test
    public void testBookingNotFound() {
        String phone = "1234567890";

        User user = new User();
        user.setNumberPhone(phone);
        userRepository.save(user);

        assertThrows(BookingNotFoundException.class, () -> adapter.findBy(phone, null));
    }

    @Test
    public void testFindBookings() {
        String phone = "1234567890";

        User user = new User();
        user.setNumberPhone(phone);
        userRepository.save(user);

        Status status = new Status();
        status.setStatus("CONFIRMED");
        testEntityManager.persist(status);
        Status merge = testEntityManager.find(Status.class, 1L);


        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setBookingTime(LocalDateTime.of(2025, 4, 1, 10, 0));
        booking.setUserPhone(user);
        booking.setStatus(merge);
        booking.setRoute("Route A");
        bookingRepository.save(booking);

        List<BookingEntity> bookingEntities = adapter.findBy(phone, null);

        assertNotNull(bookingEntities);
        assertFalse(bookingEntities.isEmpty());
        assertEquals(1, bookingEntities.size());

        BookingEntity entity = bookingEntities.get(0);
        assertEquals("booking1", entity.id());

        assertEquals("2025-04-01T10:00:00", entity.bookingTime());
        assertEquals(phone, entity.userPhone());

        assertEquals("CONFIRMED", entity.status());
        assertEquals("Route A", entity.route());
    }
    @Test
    public void testFindMultipleBookings() {
        String phone = "9876543210";

        User user = new User();
        user.setNumberPhone(phone);
        userRepository.save(user);

        Status statusConfirmed = new Status();
        statusConfirmed.setStatus("CONFIRMED");
        testEntityManager.persist(statusConfirmed);

        Status statusPending = new Status();
        statusPending.setStatus("PENDING");
        testEntityManager.persist(statusPending);

        Booking booking1 = new Booking();
        booking1.setId("booking1");
        booking1.setBookingTime(LocalDateTime.of(2025, 6, 1, 9, 0));
        booking1.setUserPhone(user);
        booking1.setStatus(statusConfirmed);
        booking1.setRoute("Route B");
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setId("booking2");
        booking2.setBookingTime(LocalDateTime.of(2025, 6, 2, 14, 30));
        booking2.setUserPhone(user);
        booking2.setStatus(statusPending);
        booking2.setRoute("Route C");
        bookingRepository.save(booking2);

        List<BookingEntity> bookingEntities = adapter.findBy(phone, null);
        assertNotNull(bookingEntities);
        assertEquals(2, bookingEntities.size());
        assertTrue(bookingEntities.stream().anyMatch(b -> "booking1".equals(b.id())));
        assertTrue(bookingEntities.stream().anyMatch(b -> "booking2".equals(b.id())));
    }

    @Test
    public void testNullPhoneThrowsException() {

        assertThrows(UserNotFoundException.class, () -> adapter.findBy(null, null));
    }

    @Test
    public void testBookingWithNullBookingTime() {
        String phone = "1122334455";
        User user = new User();
        user.setNumberPhone(phone);
        userRepository.save(user);

        Status status = new Status();
        status.setStatus("PENDING");
        testEntityManager.persist(status);

        Booking booking = new Booking();
        booking.setId("bookingNullTime");
        booking.setBookingTime(null);
        booking.setUserPhone(user);
        booking.setStatus(status);
        booking.setRoute("Route D");
        bookingRepository.save(booking);

        List<BookingEntity> bookingEntities = adapter.findBy(phone, null);
        assertNotNull(bookingEntities);
        assertEquals(1, bookingEntities.size());
        BookingEntity entity = bookingEntities.get(0);
        assertEquals("bookingNullTime", entity.id());
        assertNull(entity.bookingTime());
        assertEquals("PENDING", entity.status());
    }

}