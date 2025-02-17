package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.BookingEntity;
import org.service.ouptput_port.repository.BookingRepository;
import org.service.output_port.FindByPhoneTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindByPhoneAdapter implements FindByPhoneTransportationServiceOutputPort {

    private final BookingRepository repository;

    @Override
    public List<BookingEntity> findBy(String phone) {
        return null;
    }
}
