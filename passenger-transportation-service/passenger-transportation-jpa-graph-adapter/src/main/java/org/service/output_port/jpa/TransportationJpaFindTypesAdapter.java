package org.service.output_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.TypeEntity;

import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;
import org.service.output_port.mapper.TypeMapper;
import org.service.output_port.repository.TypeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationJpaFindTypesAdapter implements FindTypesTransportationServiceOutputPort {

    private final TypeRepository typeRepository;

    @Override
    @Cacheable("TransportationJpaFindTypesAdapter::findAllTypeEntity")
    public List<TypeEntity> findAllTypeEntity() {
        return TypeMapper.INSTANCE.transportTypesToTypeEntitys(typeRepository.findAll());
    }

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return FindTypesTransportationServiceOutputPort.class;
    }
}
