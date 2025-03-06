package org.service.passangertransportationgraphjpaadapter.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.service.passangertransportationgraphjpaadapter.dto.ParamsEntity;
import org.service.passangertransportationgraphjpaadapter.model.Route;


import java.util.Optional;

public class IdParamHandler extends Handler {

    protected IdParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        this.criteriaPredicate.add(Optional.ofNullable(entity.getRouteId())
                .filter(ids -> !ids.isEmpty())
                .map(obj -> {
                    CriteriaBuilder.In<Object> id = builder.in(root.get("id"));
                    for(var i : obj) {
                        id.value(i);
                    }
                    return id;
                }));
    }
}
