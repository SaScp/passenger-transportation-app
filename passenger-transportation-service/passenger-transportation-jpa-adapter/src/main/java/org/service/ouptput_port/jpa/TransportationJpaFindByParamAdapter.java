package org.service.ouptput_port.jpa;

import jakarta.persistence.EntityManager;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.filter_handler.HandlerExecutor;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.model.Route;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return RouteMapper.INSTANCE.routesToRouteEntitys(entityManager.createQuery(findQuery)
                .setFirstResult((pageEntity.getPageSize() * pageEntity.getPageNum()))
                .setMaxResults(pageEntity.getPageSize())
                .getResultList());
    }
}
