package org.service.output_port.find;

import org.service.entity.TypeEntity;
import org.service.output_port.TransportationServiceOutputPort;

import java.util.List;

public interface FindTypesTransportationServiceOutputPort extends TransportationServiceOutputPort {


    /**
     * @return list TypeEntity объект содержащий список из всех возможных типов поездки
     * **/
    List<TypeEntity> findAllTypeEntity();
}
