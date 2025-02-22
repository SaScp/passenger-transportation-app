package org.service.output_port;

import org.service.entity.BookingEntity;

import java.util.List;

public interface FindByPhoneTransportationServiceOutputPort extends TransportationServiceOutputPort {
     List<BookingEntity> findBy(String phone);
}
