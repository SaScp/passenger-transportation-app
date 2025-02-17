package org.service.ouptput_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.ouptput_port.filters.Handler;
import org.service.ouptput_port.filters.HandlerExecutor;
import org.service.ouptput_port.mapper.RouteMapper;
import org.service.ouptput_port.model.Route;
import org.service.ouptput_port.repository.RouteRepository;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    private EntityManager entityManager;


    @Override
    public List<RoutesEntity> findBy(ParamsEntity entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        HandlerExecutor handlerExecutor = new HandlerExecutor(builder);
        CriteriaQuery<Route> find = handlerExecutor.execute(entity);

        var query1 = entityManager.createQuery(find).getResultList();
        return RouteMapper.INSTANCE.routesToRouteEntitys(query1);
    }
}
