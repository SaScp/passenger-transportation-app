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
        CriteriaQuery<Route> find = builder.createQuery(Route.class);
        Root<Route> from = find.from(Route.class);

        HandlerExecutor executor = new HandlerExecutor(builder, from);
        List<Predicate> predicateList = executor.execute(entity);

        find.where(builder.and(predicateList.toArray(new Predicate[0])));
        find.orderBy(builder.asc(from.get("departureTime")));

        var query1 = entityManager.createQuery(find)
                .setFirstResult((pageEntity.getPageSize() * pageEntity.getPageNum()))
                .setMaxResults(pageEntity.getPageSize())
                .getResultList();

        return RouteMapper.INSTANCE.routesToRouteEntitys(query1);
    }
}
