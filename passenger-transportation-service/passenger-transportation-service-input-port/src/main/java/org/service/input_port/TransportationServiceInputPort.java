package org.service.input_port;

import org.service.entity.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TransportationServiceInputPort {


    List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity);

    GraphEntity findAll();


     List<RoutesEntity> findAll(PageEntity pageEntity);

    void createBooking(BookingParamsEntity bookingParams);

    void revokeBooking(String id);

    List<BookingEntity> findByPhone(String phone, PageEntity pageEntity);

    List<TypeEntity> findAllType();
}
