package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;

import java.util.Optional;

public class ToParamHandler extends Handler {

    protected ToParamHandler() {

    }


    @Override
    protected CriteriaBuilder next(ParamsEntity entity) {
        return null;
    }
}
