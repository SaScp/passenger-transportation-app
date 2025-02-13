package org.service.output_port;

import org.service.entity.RoutesEntity;

import java.time.ZonedDateTime;

public interface FindByParamsTransportationServiceOutputPort  {

    Iterable<RoutesEntity> findBy(ZonedDateTime time, String type);

}
