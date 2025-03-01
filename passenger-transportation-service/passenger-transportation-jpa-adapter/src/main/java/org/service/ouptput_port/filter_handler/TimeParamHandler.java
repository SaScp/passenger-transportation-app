package org.service.ouptput_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.LocalDateTimeConverter;
import org.service.ouptput_port.model.Route;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class TimeParamHandler extends Handler {

    protected TimeParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        this.criteriaPredicate.add(
                Optional.ofNullable(entity.getTime())
                        .map(obj -> builder.greaterThanOrEqualTo(root.get("departureTime"), obj))
                        .or(() -> {
                            if (entity.getRouteId() == null) {
                                return Optional.ofNullable(builder.greaterThanOrEqualTo(root.get("departureTime"), converter.convertToDatabaseColumn(LocalDateTime.now())));
                            } else {
                                return Optional.empty();
                            }
                        })
        );
    }
}
