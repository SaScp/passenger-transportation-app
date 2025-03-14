package org.service.input_port;

import org.service.entity.*;

import java.util.List;

public interface TransportationServiceInputPort {


    List<RoutesEntity> findByParams(ParamsEntity entity, PageEntity pageEntity);

    GraphEntity findAll();

     List<RoutesEntity> findAll(PageEntity pageEntity);

    void createBooking(BookingParamsEntity bookingParams);

    void revokeBooking(String id);

    List<BookingEntity> findByPhone(String phone, PageEntity pageEntity);

    List<TypeEntity> findAllType();

    GraphEntity findGraphByIds(List<String> ids);

    List<RoutesEntity> findRoutesByDepId(String id, PageEntity pageEntity);
}
