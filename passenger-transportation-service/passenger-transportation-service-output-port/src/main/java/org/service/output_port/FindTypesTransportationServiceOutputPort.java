package org.service.output_port;

import org.service.entity.TypeEntity;

import java.util.List;

public interface FindTypesTransportationServiceOutputPort {


    /**
     * @return list TypeEntity объект содержащий список из всех возможных типов поездки
     * **/
    List<TypeEntity> findAllTypeEntity();
}
