package org.service.input_port;

import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import java.util.List;
import java.util.Set;

public interface TransportationServiceInputPort {


    List<RoutesEntity> findByParams(ParamsEntity entity);

    List<RoutesEntity> findAll();
}
