package org.service.output_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.service.entity.TypeEntity;

import org.service.output_port.model.Type;

import java.util.List;

@Mapper()
public interface TypeMapper {

    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    List<TypeEntity>  transportTypesToTypeEntitys(List<Type> transportTypes);
    default String map(Type value){
        return value.getTypeName();
    }
}
