package org.service.output_purt.jpa;

import lombok.extern.slf4j.Slf4j;
import org.service.entity.BookingEntity;

import org.service.entity.PageEntity;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_purt.exception.BookingNotFoundException;
import org.service.output_purt.exception.UserNotFoundException;
import org.service.output_purt.mapper.BookingMapper;
import org.service.output_purt.model.Booking;
import org.service.output_purt.repository.BookingRepository;
import org.service.output_purt.repository.UserRepository;
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
    @Cacheable(key = "#phone + '_' + #pageEntity.hashCode()", value = "TransportationJpaFindByPhoneAdapter::findBy")
    public List<BookingEntity> findBy(String phone, PageEntity pageEntity) {

        if (!userRepository.existsById(phone)) {
            log.error("error in method {} message {}", Thread.currentThread().getStackTrace()[1], "User not found");
            throw new UserNotFoundException();
        }

        Optional<List<Booking>> optionalListBookings = repository.findAllByNumberPhone_NumberPhone(phone);
        return optionalListBookings.map(BookingMapper.INSTANCE::bookingsToBookingEntitys)
                .orElseThrow(BookingNotFoundException::new);
    }
}
