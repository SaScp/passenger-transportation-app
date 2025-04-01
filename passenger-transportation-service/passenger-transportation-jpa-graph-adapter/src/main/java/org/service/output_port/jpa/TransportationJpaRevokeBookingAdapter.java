package org.service.output_port.jpa;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.service.exception.ProblemDetailsException;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;
import org.service.output_port.exception.BookingNotFoundException;
import org.service.output_port.model.Booking;
import org.service.output_port.model.Status;
import org.service.output_port.repository.BookingRepository;
import org.service.output_port.util.CacheUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@AllArgsConstructor
public class TransportationJpaRevokeBookingAdapter implements RevokeBookingTransportationServiceOutputPort {

    private final BookingRepository repository;

    private static final Long REVOKED_STATUS_ID = 2L;

    private final EntityManager entityManager;

    private CacheUtils cacheUtils;

    @Override
    @Transactional(rollbackFor = ProblemDetailsException.class)
    public void revoke(String id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("error in method {} message {}", Thread.currentThread().getStackTrace()[1], "Booking not found");
                    return new BookingNotFoundException();
                });

        booking.setStatus(entityManager.find(Status.class, REVOKED_STATUS_ID));
        repository.save(booking);

        cacheUtils.revoke("TransportationJpaFindByPhoneAdapter::findBy", booking.getUserPhone().getNumberPhone().hashCode());
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return RevokeBookingTransportationServiceOutputPort.class;
    }
}

