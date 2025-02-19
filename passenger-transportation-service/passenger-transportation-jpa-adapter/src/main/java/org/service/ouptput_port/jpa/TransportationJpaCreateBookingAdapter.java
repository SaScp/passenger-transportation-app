package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.BookingParamsEntity;
import org.service.exception.ProblemDetailsException;
import org.service.ouptput_port.model.Booking;
import org.service.ouptput_port.model.Status;
import org.service.ouptput_port.model.User;
import org.service.ouptput_port.repository.BookingRepository;
import org.service.ouptput_port.repository.UserRepository;
import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

@Component
@Transactional
@AllArgsConstructor
public class TransportationJpaCreateBookingAdapter implements CreateBookingTransportationServiceOutputPort {

    private final BookingRepository bookingRepository;

    public static final Long CREATED_STATUS_ID = 1L;

    @Override
    public void create(BookingParamsEntity params) {
        String id = UUID.randomUUID().toString();
        Booking booking = new Booking(
                id,
                LocalDateTime.now(),
                new Status(CREATED_STATUS_ID),
                new User(params.getNumberPhone()),
                params.getRouteId()
        );
        bookingRepository.save(booking);
    }
}
