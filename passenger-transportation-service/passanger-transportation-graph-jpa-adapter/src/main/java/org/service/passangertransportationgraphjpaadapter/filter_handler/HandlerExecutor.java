package org.service.passangertransportationgraphjpaadapter.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.service.passangertransportationgraphjpaadapter.dto.ParamsEntity;
import org.service.passangertransportationgraphjpaadapter.model.Route;


import java.util.ArrayList;
import java.util.List;

public class HandlerExecutor {

    private final Handler parentHandler;

    public HandlerExecutor(CriteriaBuilder builder, Root<Route> root) {
        this.parentHandler = Handler.createHandler(builder, root);
    }

    public List<Predicate> execute(ParamsEntity entity) {
        return this.parentHandler.next(entity, new ArrayList<>()).build();
    }

}
