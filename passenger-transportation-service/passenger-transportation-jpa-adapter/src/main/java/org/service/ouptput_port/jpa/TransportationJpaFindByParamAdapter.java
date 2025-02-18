package org.service.ouptput_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.repository.RouteRepository;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private EntityManager entityManager;


    @Override
    public List<RoutesEntity> findBy(ParamsEntity entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Route> find = builder.createQuery(Route.class);
        Root<Route> root = find.from(Route.class);
        List<Predicate> predicates = Stream.of(
                        Optional.ofNullable(entity.getFrom())
                                .map(from -> builder.equal(root.get("departureCity"), from)),
                        Optional.ofNullable(entity.getTime())
                                .map(time -> builder.greaterThanOrEqualTo(root.get("departureTime"), time)),
                        Optional.ofNullable(entity.getTo())
                                .map(to -> builder.equal(root.get("arrivalCity"), to)),
                        Optional.ofNullable(entity.getType())
                                .map(type -> builder.equal(root.get("typeName"), type))
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        find.where(builder.and(predicates.toArray(new Predicate[0])));
        find.orderBy(builder.asc(root.get("departureTime")));
        var query1 = entityManager.createQuery(find).getResultList();
        return RouteMapper.INSTANCE.routesToRouteEntitys(query1);
    }
}
