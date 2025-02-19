package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.service.exception.ProblemDetailsException;
import org.service.ouptput_port.model.Booking;
import org.service.ouptput_port.model.Status;
import org.service.ouptput_port.repository.BookingRepository;

import org.service.output_port.RevokeBookingTransportationServiceOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
@Transactional(rollbackFor = ProblemDetailsException.class)
public class TransportationJpaRevokeBookingAdapter implements RevokeBookingTransportationServiceOutputPort {

    private final BookingRepository repository;

    private static final Long REVOKED_STATUS_ID = 2L;


    @Override
    public void revoke(String id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("error in method {} message {}", Thread.currentThread().getStackTrace()[1], "User not found");
                    return new ProblemDetailsException(404, "Booking Not found");
                });
        booking.setStatus(new Status(REVOKED_STATUS_ID));
        repository.save(booking);
    }
}
