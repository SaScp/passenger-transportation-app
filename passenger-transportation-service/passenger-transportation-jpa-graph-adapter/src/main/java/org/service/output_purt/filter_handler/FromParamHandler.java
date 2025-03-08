package org.service.output_purt.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.Route;


import java.util.Optional;

public class FromParamHandler extends Handler {
    protected FromParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        Join<Route, Location> locationJoin = root.join("departureCity", JoinType.INNER);
        this.criteriaPredicate.add(Optional.ofNullable(entity.getFrom())
                .filter(from -> !(from.isEmpty() && from.isBlank()))
                .map(from -> builder.equal(locationJoin.get("cName"), from)));
    }
}
