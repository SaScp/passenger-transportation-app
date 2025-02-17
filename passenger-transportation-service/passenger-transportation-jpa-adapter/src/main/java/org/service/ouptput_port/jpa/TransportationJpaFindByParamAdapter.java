package org.service.ouptput_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.repository.RouteRepository;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private final RouteRepository repository;

    private EntityManager entityManager;


    @Override
    public List<RoutesEntity> findBy(ParamsEntity entity) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Root<Route> query = entityManager.getCriteriaBuilder().createQuery().from(Route.class);
        CriteriaQuery<Route> find = entityManager.getCriteriaBuilder()
                .createQuery(Route.class)
                .select(query)
                .where(builder.greaterThan(query.get("departureTime"), entity.getTime()))
                .where(builder.equal(query.get("departureCity"), entity.getFrom()))
                .where(builder.equal(query.get("arrivalCity"), entity.getTo()))
                .where(builder.equal(query.get("typeName"), entity.getType()));
        
        var query1 = entityManager.createQuery(find).getResultList();
        return RouteMapper.INSTANCE.routesToRouteEntitys(query1);
    }
}
