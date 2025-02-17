package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.Optional;

public class ToParamHandler extends Handler {

    protected ToParamHandler(CriteriaBuilder criteriaBuilder) {
        super(criteriaBuilder);
    }


    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.getTo()).isPresent()) {
            criteriaQuery
                    .where(criteriaBuilder
                            .equal(root
                                    .get("arrivalCity"), entity.getTo())
                    );
        }
    }
}
