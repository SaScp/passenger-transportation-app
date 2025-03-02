package org.service.ouptput_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.BookingEntity;
import org.service.ouptput_port.model.Booking;
import org.service.ouptput_port.model.Status;
import org.service.ouptput_port.model.TransportType;
import org.service.ouptput_port.model.User;

import java.util.List;
import java.util.Optional;

@Mapper()
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    List<BookingEntity> bookingsToBookingEntitys(List<Booking> bookings);

    BookingEntity bookingToBookingEntity(Booking bookings);

    default String map(Status value){
        return Optional.ofNullable(value).map(Status::getStatus).orElse("not found");
    }

    default String map(User value){
        return Optional.ofNullable(value).map(User::getNumberPhone).orElse("not found");
    }
}
