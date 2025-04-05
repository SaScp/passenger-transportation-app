package org.service.input_port;

import org.service.entity.GraphEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GraphTransportationServiceInputPort {

    CompletableFuture<GraphEntity> findAll();

    CompletableFuture<GraphEntity> findGraphByIds(List<String> ids);
}
