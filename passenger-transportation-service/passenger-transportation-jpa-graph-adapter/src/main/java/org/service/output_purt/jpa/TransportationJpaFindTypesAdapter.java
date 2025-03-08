package org.service.output_purt.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.TypeEntity;

import org.service.output_port.find.FindTypesTransportationServiceOutputPort;
import org.service.output_purt.mapper.TypeMapper;
import org.service.output_purt.repository.TypeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransportationJpaFindTypesAdapter implements FindTypesTransportationServiceOutputPort {

    private final TypeRepository typeRepository;

    @Override
    public List<TypeEntity> findAllTypeEntity() {
        return TypeMapper.INSTANCE.transportTypesToTypeEntitys(typeRepository.findAll());
    }
}
