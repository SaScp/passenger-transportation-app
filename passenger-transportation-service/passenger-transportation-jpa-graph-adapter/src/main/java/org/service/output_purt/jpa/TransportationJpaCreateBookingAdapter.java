package org.service.output_purt.jpa;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.service.entity.BookingParamsEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_purt.exception.RouteIsNullException;
import org.service.output_purt.mapper.BookingMapper;
import org.service.output_purt.model.Booking;
import org.service.output_purt.model.Status;
import org.service.output_purt.model.User;
import org.service.output_purt.repository.BookingRepository;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class TransportationJpaCreateBookingAdapter implements CreateBookingTransportationServiceOutputPort {

    private final BookingRepository bookingRepository;

    private final EntityManager entityManager;

    private final CacheManager cacheManager;

    public static final Long CREATED_STATUS_ID = 1L;

    @Override
    public void create(BookingParamsEntity entity) {
        User user = Optional.ofNullable(entityManager.find(User.class, entity.getNumberPhone()))
                .orElse(new User(entity.getNumberPhone()));
        if (entity.getRouteId() == null) {
            throw new RouteIsNullException();
        }
        String id = UUID.randomUUID().toString();
        try {
            Booking newBooking = new Booking(
                    id,
                    LocalDateTime.now(),
                    entityManager.find(Status.class, CREATED_STATUS_ID),
                    user,
                    entity.getRouteId()
            );
            Booking save = bookingRepository.save(newBooking);

            Optional.ofNullable(cacheManager.getCache("TransportationJpaFindByPhoneAdapter::findBy"))
                    .ifPresent(
                            cache -> Optional.ofNullable(cache.get(entity.getNumberPhone(), List.class))
                                    .map(e -> e.add(BookingMapper.INSTANCE.bookingToBookingEntity(save)))
                    );

        } catch (Exception e) {
            log.error("\nerror {} \nin {} \nmessage {}", e.getClass(), e.getStackTrace()[1], e.getMessage());
            throw new ProblemDetailsException(500, "ошибка при сохранении");
        }
    }
}
