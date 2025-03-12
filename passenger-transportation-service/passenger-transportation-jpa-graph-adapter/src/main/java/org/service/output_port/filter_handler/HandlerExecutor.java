package org.service.output_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.output_port.model.Route;


import java.util.ArrayList;
import java.util.List;

public class HandlerExecutor {

    private final Handler parentHandler;

    public HandlerExecutor(CriteriaBuilder builder, Root<Route> root) {
        this.parentHandler = Handler.createHandler(builder, root);
    }

    public HandlerExecutor(Handler parentHandler) {
        this.parentHandler = parentHandler;
    }

    public List<Predicate> execute(ParamsEntity entity) {
        return this.parentHandler.next(entity, new ArrayList<>()).build();
    }

}
