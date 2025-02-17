package org.service.ouptput_port.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.sqm.tree.select.SqmSortSpecification;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class Handler {

    protected final CriteriaBuilder criteriaBuilder;

    protected CriteriaQuery<Route> criteriaQuery;

    protected Root<Route> root;

    protected Handler nextNode;

    protected Handler(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public static Handler createHandler(CriteriaBuilder criteriaBuilder) {
        FromParamHandler fromParamHandler = new FromParamHandler(criteriaBuilder);
        TimeParamHandler timeParamHandler = new TimeParamHandler(criteriaBuilder);
        ToParamHandler toParamHandler = new ToParamHandler(criteriaBuilder);
        TypeParamHandler typeParamHandler = new TypeParamHandler(criteriaBuilder);
        fromParamHandler.nextNode = timeParamHandler;
        timeParamHandler.nextNode = toParamHandler;
        toParamHandler.nextNode = typeParamHandler;

        return fromParamHandler;
    }

    public Handler next(ParamsEntity entity, CriteriaQuery<Route> criteriaQuery, Root<Route> root) {
        this.criteriaQuery = criteriaQuery;
        this.root = root;
        addParam(entity);
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this;
        }
        return nextNode.next(entity, this.criteriaQuery, root);
    }

    public CriteriaQuery<Route> build() {
        return this.criteriaQuery.orderBy(criteriaBuilder.asc(root.get("departureTime")));
    }

    protected abstract void addParam(ParamsEntity entity);


}
