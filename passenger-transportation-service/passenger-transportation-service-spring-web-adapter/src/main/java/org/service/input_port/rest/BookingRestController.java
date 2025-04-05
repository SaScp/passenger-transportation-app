package org.service.input_port.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.input_port.BookingTransportationServiceInputPort;
import org.service.input_port.annotation.PageSettingParam;
import org.service.input_port.request.BookingQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async("taskExecutor")
@RequestMapping("/booking")
@RestController
@Tag(name = "GraphRestController", description = "Контроллер для взаимодействия с бронированием")
public class BookingRestController {

    private final BookingTransportationServiceInputPort inputPort;

    public BookingRestController(BookingTransportationServiceInputPort inputPort) {
        this.inputPort = inputPort;
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
    public CompletableFuture<List<BookingEntity>> findTransportByPhone(@RequestParam(value = "phone") String phone, @Parameter(hidden = true) @PageSettingParam PageEntity pageEntity) {

        return this.inputPort.findByPhone(phone, pageEntity);
    }

}
