package org.service.output_port;

import org.service.entity.RoutesEntity;

import java.util.List;
import java.util.Set;

public interface FindAllTransportationServiceOutputPort extends TransportationServiceOutputPort {

    List<RoutesEntity> findAll();

}
