package org.service.ouptput_port.jpa;

import lombok.AllArgsConstructor;
import org.service.entity.TypeEntity;
import org.service.ouptput_port.mapper.TypeMapper;
import org.service.ouptput_port.repository.TypeRepository;
import org.service.output_port.FindTypesTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindTypesAdapter implements FindTypesTransportationServiceOutputPort {

    private final TypeRepository typeRepository;

    @Override
    public List<TypeEntity> findAllTypeEntity() {
        return TypeMapper.INSTANCE.transportTypesToTypeEntitys(typeRepository.findAll());
    }
}
