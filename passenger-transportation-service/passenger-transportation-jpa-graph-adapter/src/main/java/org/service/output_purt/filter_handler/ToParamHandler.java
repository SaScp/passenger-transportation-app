package org.service.output_purt.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.Route;


import java.util.Optional;

public class ToParamHandler extends Handler {

    protected ToParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        Join<Route, Location> locationJoin = root.join("arrivalCity", JoinType.INNER);

        this.criteriaPredicate.add(Optional.ofNullable(entity.getTo())
                .filter(to -> !(to.isEmpty() && to.isBlank()))
                .map(obj -> builder.equal(locationJoin.get("cName"), obj)));
    }
}
