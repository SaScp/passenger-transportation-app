package org.service.ouptput_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.Optional;

public class IdParamHandler extends Handler {

    protected IdParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        this.criteriaPredicate.add(Optional.ofNullable(entity.getRouteId())
                .filter(id -> !(id.isEmpty() && id.isBlank()))
                .map(obj -> builder.equal(root.get("id"), obj)));
    }
}
