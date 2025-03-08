package org.service.output_port.find;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.TransportationServiceOutputPort;

import java.util.List;
import java.util.Set;

public interface FindAllTransportationServiceOutputPort extends TransportationServiceOutputPort {


    /**
     * @param pageEntity параметр для пагинации страниц
     * @return List routeEntity объект содержащий список из найденных маршрутов
     * **/
    List<RoutesEntity> findAll(PageEntity pageEntity);

}
