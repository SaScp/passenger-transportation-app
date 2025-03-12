package org.service.output_port.filter_handler;

import jakarta.persistence.criteria.*;
import org.service.entity.ParamsEntity;
import org.service.output_port.model.Location;
import org.service.output_port.model.Route;


import java.util.Optional;

public class FromParamHandler extends Handler {
    protected FromParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        this.criteriaPredicate.add(Optional.ofNullable(entity.getFrom())
                .filter(from -> !(from.isEmpty() && from.isBlank()))
                .map(from -> {
                    Join<Route, Location> locationJoin = root.join("departureCity", JoinType.INNER);
                    return builder.equal(locationJoin.get("cName"), from);
                }));
    }
}
