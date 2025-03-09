package org.service.output_purt.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_purt.filter_handler.HandlerExecutor;
import org.service.output_purt.mapper.RouteMapper;
import org.service.output_purt.model.Route;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private EntityManager entityManager;

    @Override
    @Cacheable(key = "#entity.hashCode() % #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Route> findQuery = builder.createQuery(Route.class);
        Root<Route> rootObj = findQuery.from(Route.class);

        HandlerExecutor handlerExecutor = new HandlerExecutor(builder, rootObj);
        List<Predicate> predicateFilterList = handlerExecutor.execute(entity);

        findQuery.where(builder.and(predicateFilterList.toArray(new Predicate[0])));
        findQuery.orderBy(builder.asc(rootObj.get("departureTime")));

        List<Route> resultList = entityManager.createQuery(findQuery)
                .setFirstResult((pageEntity.getPageSize() * pageEntity.getPageNum()))
                .setMaxResults(pageEntity.getPageSize())
                .getResultList();
        return RouteMapper.INSTANCE.routesToRouteEntitys(resultList);
    }
}
