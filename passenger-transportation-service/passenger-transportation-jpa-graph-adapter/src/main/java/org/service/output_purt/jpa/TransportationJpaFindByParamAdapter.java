package org.service.output_purt.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_purt.filter_handler.HandlerExecutor;
import org.service.output_purt.mapper.RouteMapper;
import org.service.output_purt.model.Location;
import org.service.output_purt.model.Route;
import org.service.output_purt.model.RouteStep;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(key = "#entity.hashCode() % #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> idQuery = builder.createQuery(String.class);
        Root<Route> rootObj = idQuery.from(Route.class);

        HandlerExecutor handlerExecutor = new HandlerExecutor(builder, rootObj);
        List<Predicate> predicateFilterList = handlerExecutor.execute(entity);

        idQuery.select(rootObj.get("id"));
        if (!predicateFilterList.isEmpty()) {
            idQuery.where(builder.and(predicateFilterList.toArray(new Predicate[0])));
        }

        idQuery.orderBy(builder.asc(rootObj.get("departureTime")));
        TypedQuery<String> typedIdQuery = entityManager.createQuery(idQuery);
        typedIdQuery.setFirstResult(pageEntity.getPageNum() * pageEntity.getPageSize());
        typedIdQuery.setMaxResults(pageEntity.getPageSize());


        List<String> routeIds = typedIdQuery.getResultList();
        if (routeIds.isEmpty()) return List.of();

        CriteriaQuery<Route> findQuery = builder.createQuery(Route.class);
        Root<Route> root = findQuery.from(Route.class);

        Fetch<Route, RouteStep> routeStepsFetch = root.fetch("routeSteps");
        Fetch<RouteStep, Location> edgeId = routeStepsFetch.fetch("edgeId");

        edgeId.fetch("fromLocationId");
        edgeId.fetch("toLocationId");



        findQuery.select(root).where(root.get("id").in(routeIds))
                .orderBy(builder.asc(root.get("departureTime")));

        var resultList = entityManager.createQuery(findQuery).getResultList();


        return RouteMapper.INSTANCE.routesToRouteEntitys(resultList);
    }
}
