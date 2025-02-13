package org.service.output_port;

import org.service.entity.RoutesEntity;

public interface FindAllTransportationServiceOutputPort  {

    Iterable<RoutesEntity> findAll();

}
