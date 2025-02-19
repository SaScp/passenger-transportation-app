package org.service.ouptput_port.repository;

import org.service.ouptput_port.model.Booking;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "select booking from Booking booking inner join fetch booking.status  where booking.userPhone.numberPhone = :number_phone")
    Optional<List<Booking>> findAllByNumberPhone_NumberPhone(@Param("number_phone") String numberPhone);
}
