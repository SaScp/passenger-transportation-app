package org.service.input_port;

import org.service.entity.GraphEntity;

import java.util.List;

public interface GraphTransportationServiceInputPort {

    GraphEntity findAll();

    GraphEntity findGraphByIds(List<String> ids);
}
