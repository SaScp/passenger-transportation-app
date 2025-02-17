package org.service.ouptput_port.filters;



import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;

import java.util.ArrayList;
import java.util.List;

public class HandlerExecutor {

    private Handler parentHandler;

    private final CriteriaBuilder criteriaBuilder;

    public HandlerExecutor(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        this.parentHandler = Handler.createHandler(this.criteriaBuilder);
    }

    public CriteriaQuery<Route> execute(ParamsEntity entity) {
        CriteriaQuery<Route> query = criteriaBuilder.createQuery(Route.class);
        Root<Route> root = query.from(Route.class); // Создаём Root один раз!
        return this.parentHandler.next(entity, query, root).build();
    }
}
