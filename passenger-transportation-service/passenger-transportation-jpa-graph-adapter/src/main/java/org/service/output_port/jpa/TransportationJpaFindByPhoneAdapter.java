package org.service.output_port.jpa;

import lombok.extern.slf4j.Slf4j;
import org.service.entity.BookingEntity;

import org.service.entity.PageEntity;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.exception.BookingNotFoundException;
import org.service.output_port.exception.UserNotFoundException;
import org.service.output_port.mapper.BookingMapper;
import org.service.output_port.model.Booking;
import org.service.output_port.repository.BookingRepository;
import org.service.output_port.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Transactional(readOnly = true)
public class TransportationJpaFindByPhoneAdapter implements FindByPhoneTransportationServiceOutputPort {

    private final BookingRepository repository;

    private final UserRepository userRepository;


    public TransportationJpaFindByPhoneAdapter(BookingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BookingEntity> findBy(String phone, PageEntity pageEntity) {

        if (phone == null || !userRepository.existsById(phone)) {
            log.error("error in method {} message {}", Thread.currentThread().getStackTrace()[1], "User not found");
            throw new UserNotFoundException();
        }

        Optional<List<Booking>> optionalListBookings = repository.findAllByNumberPhone_NumberPhone(phone);

        return optionalListBookings.map(BookingMapper.INSTANCE::bookingsToBookingEntitys)
                .map(e -> {
                    if (e.isEmpty()) {
                        throw new BookingNotFoundException();
                    } else  {
                        return e;
                    }
                })
                .orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindByPhoneTransportationServiceOutputPort.class;
    }
}
