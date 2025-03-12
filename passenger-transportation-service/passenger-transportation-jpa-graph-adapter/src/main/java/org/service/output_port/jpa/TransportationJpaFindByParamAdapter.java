package org.service.output_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.filter_handler.HandlerExecutor;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.model.Route;
import org.service.output_port.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    @PersistenceContext
    private final EntityManager entityManager;

    private final RouteRepository repository;

    @Override
    @Cacheable(key = "#entity.hashCode() % #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {

        List<String> routeIds = null;

        if (entity.getRouteId() == null || entity.getRouteId().isEmpty()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> idQuery = builder.createQuery(String.class);

            Root<Route> rootObj = idQuery.from(Route.class);


            HandlerExecutor handlerExecutor = new HandlerExecutor(builder, rootObj);
            List<Predicate> predicateFilterList = handlerExecutor.execute(entity);

            idQuery.select(rootObj.get("id"));
            if (!predicateFilterList.isEmpty()) {
                idQuery.where(builder.and(predicateFilterList.toArray(new Predicate[0])));
            }
            TypedQuery<String> typedIdQuery = entityManager.createQuery(idQuery);

            typedIdQuery.setFirstResult(pageEntity.getPageNum() * pageEntity.getPageSize());
            typedIdQuery.setMaxResults(pageEntity.getPageSize());

            routeIds = typedIdQuery.getResultList();
            if (routeIds.isEmpty()) return List.of();
        } else {
            routeIds = entity.getRouteId();
        }



        var resultList = repository.findRoutesByIdIn(routeIds, Sort.by(Sort.Order.by("departureTime")));

        return RouteMapper.INSTANCE.routesToRouteEntitys(resultList);
    }
}
