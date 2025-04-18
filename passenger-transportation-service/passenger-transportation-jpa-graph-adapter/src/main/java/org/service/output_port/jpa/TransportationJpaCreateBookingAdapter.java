package org.service.output_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.service.entity.BookingParamsEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.exception.RouteIsNullException;
import org.service.output_port.exception.RouteNotFoundException;
import org.service.output_port.mapper.BookingMapper;
import org.service.output_port.model.Booking;
import org.service.output_port.model.Route;
import org.service.output_port.model.Status;
import org.service.output_port.model.User;
import org.service.output_port.repository.BookingRepository;
import org.service.output_port.util.CacheUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
@AllArgsConstructor
public class TransportationJpaCreateBookingAdapter implements CreateBookingTransportationServiceOutputPort {

    private final BookingRepository bookingRepository;

    private final EntityManager entityManager;

    private final CacheUtils cacheUtils;

    public static final Long CREATED_STATUS_ID = 1L;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public void create(BookingParamsEntity entity) {
        if (entity.routeId() == null) {
            throw new RouteIsNullException();
        }

        if (entityManager.find(Route.class, entity.routeId()) == null) {
            throw new RouteNotFoundException();
        }


        User user = findOrCreate(entity);


        String id = UUID.randomUUID().toString();

        checkCause(() -> {
            Booking newBooking = new Booking(
                    id,
                    LocalDateTime.now(),
                    entityManager.find(Status.class, CREATED_STATUS_ID),
                    user,
                    entity.routeId()
            );

            Booking save = bookingRepository.save(newBooking);

            cacheUtils.createBooking("TransportationJpaFindByPhoneAdapter::findBy", entity.numberPhone().hashCode(), BookingMapper.INSTANCE.bookingToBookingEntity(save));
        });

    }

    private User findOrCreate(BookingParamsEntity entity) {
        User user = entityManager.find(User.class, entity.numberPhone());
        if (user != null) {
            return user;
        } else {
            lock.lock();
            try {
                User userTh = entityManager.find(User.class, entity.numberPhone());
                if (userTh != null) {
                    return userTh;
                }
                user = new User(entity.numberPhone());
                entityManager.persist(user);
                return user;
            } finally {
                lock.unlock();
            }
        }
    }

    private void checkCause(Runnable bookingConsumer) {
        Exception exception = null;
        try {
           bookingConsumer.run();
        } catch (ProblemDetailsException e) {
            exception = e;
            throw new ProblemDetailsException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            exception = e;
            throw new ProblemDetailsException(500, "error of create booking");
        } finally {
            if (exception != null)
                log.error("\nerror {} \nin {} \nmessage {}", exception.getClass(), exception.getStackTrace()[1], exception.getMessage());
        }
    }



    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return CreateBookingTransportationServiceOutputPort.class;
    }
}
