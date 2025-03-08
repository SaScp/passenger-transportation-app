package org.service.output_purt.jpa;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.service.exception.ProblemDetailsException;

import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;
import org.service.output_purt.exception.BookingNotFoundException;
import org.service.output_purt.model.Booking;
import org.service.output_purt.model.Status;
import org.service.output_purt.repository.BookingRepository;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Component
@AllArgsConstructor
@Transactional(rollbackFor = ProblemDetailsException.class)
public class TransportationJpaRevokeBookingAdapter implements RevokeBookingTransportationServiceOutputPort {

    private final BookingRepository repository;

    private static final Long REVOKED_STATUS_ID = 2L;

    private final EntityManager entityManager;


    private CacheManager cacheManager;

    @Override
    public void revoke(String id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("error in method {} message {}", Thread.currentThread().getStackTrace()[1], "Booking not found");
                    return new BookingNotFoundException();
                });

        booking.setStatus(entityManager.find(Status.class, REVOKED_STATUS_ID));
        repository.save(booking);

        Optional.ofNullable(cacheManager
                        .getCache("TransportationJpaFindByPhoneAdapter::findBy"))
                .ifPresent(cache -> cache.evictIfPresent(booking.getUserPhone().getNumberPhone()));
    }

}

