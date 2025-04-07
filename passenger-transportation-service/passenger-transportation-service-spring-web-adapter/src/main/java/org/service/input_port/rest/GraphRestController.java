package org.service.input_port.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.service.entity.GraphEntity;
import org.service.input_port.GraphTransportationServiceInputPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async("taskExecutor")
@RestController
@RequestMapping("/graph/")
@Tag(name = "GraphRestController", description = "Контроллер для взаимодействия с графом")
public class GraphRestController {

    private final GraphTransportationServiceInputPort inputPort;

    public GraphRestController(GraphTransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @Operation(
            summary = "Просмотр всех маршрутов",
            description = "позволяет просматривать все маршруты"
    )
    @GetMapping("/find-all-graph")
    public CompletableFuture<GraphEntity> findAllTransport() {
        return this.inputPort.findAll();
    }


    @GetMapping("/find-by-ids")
    public CompletableFuture<GraphEntity> findGraphByIds(@RequestParam("id") List<String> ids) {
        return  this.inputPort.findGraphByIds(ids);
    }
}
