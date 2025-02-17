package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.Optional;

public class FromParamHandler extends Handler {
    protected FromParamHandler(CriteriaBuilder criteriaBuilder) {
        super(criteriaBuilder);
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.getFrom()).isPresent()) {
            criteriaQuery
                    .where(criteriaBuilder
                            .equal(root.get("departureCity"), entity.getFrom())
                    );
        }
    }
}
