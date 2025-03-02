package org.service.ouptput_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.TypeEntity;
import org.service.ouptput_port.model.TransportType;

import java.util.List;

@Mapper()
public interface TypeMapper {

    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    List<TypeEntity>  transportTypesToTypeEntitys(List<TransportType> transportTypes);
    default String map(TransportType value){
        return value.getTransportType();
    }
}
