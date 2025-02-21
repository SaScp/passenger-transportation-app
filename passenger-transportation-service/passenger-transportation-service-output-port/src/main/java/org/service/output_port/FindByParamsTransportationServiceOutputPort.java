package org.service.output_port;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface FindByParamsTransportationServiceOutputPort extends TransportationServiceOutputPort {

    List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity);

}
