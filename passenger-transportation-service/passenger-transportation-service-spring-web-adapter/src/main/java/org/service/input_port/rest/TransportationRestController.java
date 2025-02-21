package org.service.input_port.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.service.entity.*;

import org.service.input_port.TransportationServiceInputPort;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.annotation.PageSettingParam;
import org.service.input_port.request.FilterParamEntity;
import org.service.input_port.request.BookingQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
    public List<RoutesEntity> findTransport(
            @PageSettingParam PageEntity pageEntity,
            @Parameter(hidden = true) @FindByParam FilterParamEntity filterParam) {
        return this.inputPort.findByParams(
                new ParamsEntity(
                        filterParam.getTime(),
                        filterParam.getType(),
                        filterParam.getFrom(),
                        filterParam.getTo()
                ),
                pageEntity
        );
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
    public List<RoutesEntity> findAllTransport(@PageSettingParam PageEntity pageEntity) {
        return this.inputPort.findAll(pageEntity);
    }

    @Operation(
            summary = "Создание брони",
            description = "Позволяет создать новую бронь"
    )
    @PostMapping(value = "/create")
    public ResponseEntity<?> booking(@RequestBody BookingQueryParam query) {
        this.inputPort.createBooking(
                new BookingParamsEntity(query.getNumberPhone(), query.getRouteId()));
        return ResponseEntity.created(URI.create("/create")).build();
    }

    @Operation(
            summary = "Отмена брони",
            description = "Позволяет отменить бронь"
    )
    @DeleteMapping("/revoke")
    public void revokeBooking(@RequestParam(name = "booking_id") String id) {
        this.inputPort.revokeBooking(id);
    }

    @Operation(
            summary = "Просмотр всех маршрутов пользователя",
            description = "Позволяет посмотреть все маршруты пользователя"
    )
    @GetMapping("/find-by-phone")
    public List<BookingEntity> findTransportByPhone(@RequestParam(value = "phone") String phone) {
        return this.inputPort.findByPhone(phone); ///return this.inputPort.
    }

    @Operation(
            summary = "Просмотр маршрута по id",
            description = "Позволяет посмотреть просмотреть маршрут по id"
    )
    @GetMapping("/find-by-id")
    public List<RoutesEntity> findTransportById( @PageSettingParam PageEntity pageEntity,
                                                 @RequestParam(value = "route_id") String id) {
        return this.inputPort.findByParams(new ParamsEntity(id), pageEntity);
    }
}
