package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;

import java.util.Optional;

public class TimeParamHandler extends Handler{

    protected TimeParamHandler() {
    }


    @Override
    protected CriteriaBuilder next(ParamsEntity entity) {
        return null;
    }
}
