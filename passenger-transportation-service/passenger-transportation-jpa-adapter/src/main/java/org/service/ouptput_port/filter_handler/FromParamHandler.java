package org.service.ouptput_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.Optional;

public class FromParamHandler extends Handler{
    protected FromParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }
    @Override
    protected void addParam(ParamsEntity entity) {
        this.criteriaPredicate.add(Optional.ofNullable(entity.getFrom())
                .map(from -> builder.equal(root.get("departureCity"), from)));
    }
}
