package org.service.output_port.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.service.entity.EdgeEntity;
import org.service.output_port.model.Edge;

import java.util.List;

@Mapper
public interface EdgeMapper {

    EdgeMapper INSTANCE = Mappers.getMapper(EdgeMapper.class);

    @Mapping(target = "type", source = "type.typeName")
    @Mapping(target = "fromLocationId.label", source = "fromLocationId.CName")
    @Mapping(target = "toLocationId.label", source = "toLocationId.CName")
    EdgeEntity edgeToEdgeEntity(Edge edge);

    List<EdgeEntity> edgesToEdgeEntitys(List<Edge> edges);
}
