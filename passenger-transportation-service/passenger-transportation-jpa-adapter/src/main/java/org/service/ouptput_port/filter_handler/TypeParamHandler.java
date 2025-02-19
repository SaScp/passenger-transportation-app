package org.service.ouptput_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.model.TransportType;

import java.util.Optional;

public class TypeParamHandler extends Handler {

    protected TypeParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        Join<Route, TransportType> typeJoin = root.join("transportType");
        this.criteriaPredicate.add(Optional.ofNullable(entity.getType())
                .map(obj -> builder.equal(typeJoin.get("transportType"), obj)));
    }
}
