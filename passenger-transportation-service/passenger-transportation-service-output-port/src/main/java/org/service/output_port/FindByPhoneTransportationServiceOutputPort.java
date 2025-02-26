package org.service.output_port;

import org.service.entity.BookingEntity;

import java.util.List;

public interface FindByPhoneTransportationServiceOutputPort extends TransportationServiceOutputPort {

     /**
      * @param phone номер телефона
      * @return list BookingEntity объект содержащий список из найденных броней привязанных к введенному номеру телефона
      * **/
     List<BookingEntity> findBy(String phone);
}
