package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.service.entity.ParamsEntity;

public abstract class Handler {

    protected CriteriaBuilder criteriaBuilder;

    protected Handler nextNode;

    public static Handler createHandler(CriteriaBuilder criteriaBuilder) {
        FromParamHandler fromParamHandler = new FromParamHandler();
        TimeParamHandler timeParamHandler = new TimeParamHandler();
        ToParamHandler toParamHandler = new ToParamHandler();
        TypeParamHandler typeParamHandler = new TypeParamHandler();
        fromParamHandler.nextNode = timeParamHandler;
        timeParamHandler.nextNode = toParamHandler;
        toParamHandler.nextNode = typeParamHandler;

        return fromParamHandler;
    }

    protected abstract CriteriaBuilder next(ParamsEntity entity);
}
