package org.service.output_port.filter_handler;

import jakarta.persistence.criteria.*;
import org.service.entity.ParamsEntity;
import org.service.output_port.model.Location;
import org.service.output_port.model.Route;


import java.util.Optional;

public class ToParamHandler extends Handler {

    protected ToParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {

        this.criteriaPredicate.add(Optional.ofNullable(entity.getTo())
                .filter(to -> !(to.isEmpty() && to.isBlank()))
                .map(obj -> {
                    Join<Route, Location> locationJoin = root.join("arrivalCity", JoinType.INNER);
                    return builder.equal(locationJoin.get("cName"), obj);
                }));
    }
}
