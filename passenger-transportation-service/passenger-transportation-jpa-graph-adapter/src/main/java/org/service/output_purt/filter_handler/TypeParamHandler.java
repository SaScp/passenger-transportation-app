package org.service.output_purt.filter_handler;

import jakarta.persistence.criteria.*;
import org.service.entity.ParamsEntity;
import org.service.output_purt.model.*;
import org.service.output_purt.repository.TypeRepository;

import java.util.Optional;

public class TypeParamHandler extends Handler {

    protected TypeParamHandler(CriteriaBuilder builder, Root<Route> root) {
        super(builder, root);
    }

    @Override
    protected void addParam(ParamsEntity entity) {

        Subquery<Long> subquery = builder.createQuery().subquery(Long.class);
        Root<RouteStep> subRouteStep = subquery.from(RouteStep.class);
        Join<RouteStep, Edge> subEdge = subRouteStep.join("edgeId");



        this.criteriaPredicate.add(Optional.ofNullable(entity.getType())
                .filter(type -> !(type.isEmpty() && type.isBlank()))
                .map(obj -> {
                   return builder.not(builder.exists(subquery.select(builder.literal(1L))
                            .where(
                                    builder.equal(subRouteStep.get("routeId"), root.get("id")),
                                    builder.notEqual(subEdge.get("cType"), obj)
                            )));

                })
                .or(() -> {
                    subquery.select(builder.literal(1L))
                            .where(builder.equal(subRouteStep.get("routeId"), root.get("id")))
                            .groupBy(subRouteStep.get("routeId"))
                            .having(builder.greaterThan(builder.countDistinct(subEdge.get("cType")), 1L));

                    return Optional.of(builder.exists(subquery));
                }));
    }
}
