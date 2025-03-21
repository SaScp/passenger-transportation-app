package org.service.input_port.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.service.entity.*;

import org.service.input_port.TransportationServiceInputPort;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.annotation.PageSettingParam;
import org.service.input_port.request.FilterParamEntity;
import org.service.input_port.request.BookingQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Async
@RestController
public class TransportationRestController {

    private final TransportationServiceInputPort inputPort;

    public TransportationRestController(TransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
    }

    @Operation(
            summary = "Просмотр маршрутов по фильтру",
            description = "позволяет просматривать первые 5 подходящих маршрутов по фильтру",
            parameters = {
                    @Parameter(
                            name = "time",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "from",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "to",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "type",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "page_num",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "page_size",
                            required = true,
                            allowEmptyValue = true)
            }
    )
    @GetMapping("/find")
    public CompletableFuture<List<RoutesEntity>> findTransport(
            @PageSettingParam PageEntity pageEntity,
            @Parameter(hidden = true) @FindByParam FilterParamEntity filterParam) {
        return CompletableFuture.supplyAsync(() ->this.inputPort.findByParams(
                new ParamsEntity(
                        filterParam.getTime(),
                        filterParam.getType(),
                        filterParam.getFrom(),
                        filterParam.getTo()
                ),
                pageEntity
        ));
    }

    @Operation(
            summary = "Просмотр всех маршрутов",
            description = "позволяет просматривать все маршруты"
    )
    @GetMapping("/find-all-graph")
    public CompletableFuture<GraphEntity> findAllTransport() {
        return CompletableFuture.supplyAsync(this.inputPort::findAll);
    }

    @Operation(
            summary = "Просмотр всех маршрутов",
            description = "позволяет просматривать все маршруты",
            parameters = {
                    @Parameter(name = "page_num",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "page_size",
                            required = true,
                            allowEmptyValue = true)
            }
    )
    @GetMapping("/find-all")
    public CompletableFuture<List<RoutesEntity>> findAllTransport(@Parameter(hidden = true) @PageSettingParam PageEntity pageEntity) {
        return CompletableFuture.supplyAsync(() -> this.inputPort.findAll(pageEntity));
    }

    @Operation(
            summary = "Создание брони",
            description = "Позволяет создать новую бронь"
    )
    @PostMapping(value = "/create")
    public CompletableFuture<ResponseEntity<?>> booking(@RequestBody BookingQueryParam query) {
        this.inputPort.createBooking(
                new BookingParamsEntity(query.getNumberPhone(), query.getRouteId()));
        return CompletableFuture.supplyAsync(() -> ResponseEntity.created(URI.create("/create")).build());
    }

    @Operation(
            summary = "Отмена брони",
            description = "Позволяет отменить бронь"
    )
    @DeleteMapping("/revoke")
    public void revokeBooking(@RequestParam(name = "booking_id") String id) {
       CompletableFuture.runAsync(() -> this.inputPort.revokeBooking(id));
    }

    @Operation(
            summary = "Просмотр всех маршрутов пользователя",
            description = "Позволяет посмотреть все маршруты пользователя",
            parameters = {
            @Parameter(name = "page_num",
                    required = true,
                    allowEmptyValue = true),
            @Parameter(name = "page_size",
                    required = true,
                    allowEmptyValue = true)
    }
    )

    @GetMapping("/find-by-phone")
    public CompletableFuture<List<BookingEntity>> findTransportByPhone(@RequestParam(value = "phone") String phone,@Parameter(hidden = true) @PageSettingParam PageEntity pageEntity) {
        return CompletableFuture.supplyAsync(() -> this.inputPort.findByPhone(phone, pageEntity));
    }

    @Operation(
            summary = "Просмотр маршрута по id",
            description = "Позволяет посмотреть просмотреть маршрут по id",
            parameters = {
                    @Parameter(name = "page_num",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "page_size",
                            required = true,
                            allowEmptyValue = true)
                    }
    )
    @GetMapping("/find-by-id")
    public CompletableFuture<List<RoutesEntity>> findTransportById(@Parameter(hidden = true) @PageSettingParam PageEntity pageEntity,
                                                 @RequestParam(value = "route_id") List<String> id) {
        return CompletableFuture.supplyAsync(() -> this.inputPort.findByParams(new ParamsEntity(id), pageEntity));
    }


    @Operation(
            summary = "Просмотр маршрутов по id города отправления",
            description = "позволяет просматривать все маршруты по id города отправления",
            parameters = {
                    @Parameter(name = "page_num",
                            required = true,
                            allowEmptyValue = true),
                    @Parameter(name = "page_size",
                            required = true,
                            allowEmptyValue = true)
            }
    )
    @GetMapping("/find-routes-by-dep-id")
    public CompletableFuture<List<RoutesEntity>> findTransportByDepId(@Parameter(hidden = true) @PageSettingParam PageEntity pageEntity, @RequestParam(value = "id") String id) {
        return CompletableFuture.supplyAsync(() -> this.inputPort.findRoutesByDepId(id, pageEntity));
    }


    @Operation(
            summary = "Просмотр всех типов поездки",
            description = "позволяет просматривать все типы поездки"
    )
    @GetMapping("/find-types")
    public CompletableFuture<List<TypeEntity>> findTypes() {
        return CompletableFuture.supplyAsync(this.inputPort::findAllType);
    }

    @GetMapping("/find-by-ids")
    public CompletableFuture<GraphEntity> findGraphByIds(@RequestParam("id") List<String> ids) {
        return CompletableFuture.supplyAsync(() -> this.inputPort.findGraphByIds(ids));
    }
}
