package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.Optional;

public class TimeParamHandler extends Handler {

    protected TimeParamHandler(CriteriaBuilder criteriaBuilder) {
        super(criteriaBuilder);
    }


    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.getTime()).isPresent()) {
            criteriaQuery
                    .where(criteriaBuilder
                            .greaterThan(root
                                    .get("departureTime"), entity.getTime())
                    );
        }
    }
}
