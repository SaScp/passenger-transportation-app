package org.service.output_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;

import org.service.output_port.model.Route;


import java.time.LocalDateTime;
import java.util.Optional;

public class TimeParamHandler extends Handler {

    protected TimeParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
      /*  LocalDateTimeConverter converter = new LocalDateTimeConverter();
        this.criteriaPredicate.add(
                Optional.ofNullable(entity.getTime())
                        .map(obj -> {
                            System.out.println(converter.convertToDatabaseColumn(obj));
                            return builder.greaterThanOrEqualTo(root.get("departureTime"), converter.convertToDatabaseColumn(obj));
                        })
                        .or(() -> {
                            if (entity.getRouteId() == null) {
                                System.out.println(converter.convertToDatabaseColumn(LocalDateTime.now()));
                                return Optional.ofNullable(builder.greaterThanOrEqualTo(root.get("departureTime"), LocalDateTime.now()));
                            } else {
                                return Optional.empty();
                            }
                        })
        );*/
    }
}
