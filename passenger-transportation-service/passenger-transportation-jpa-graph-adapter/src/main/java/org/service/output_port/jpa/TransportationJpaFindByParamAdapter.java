package org.service.output_port.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;

import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.filter_handler.HandlerExecutor;
import org.service.output_port.mapper.RouteMapper;
import org.service.output_port.model.Edge;
import org.service.output_port.model.Route;
import org.service.output_port.model.RouteEntityTemp;
import org.service.output_port.model.RouteStep;
import org.service.output_port.repository.EdgeRepository;
import org.service.output_port.repository.RouteRepository;
import org.service.output_port.repository.RouteStepRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Component
@AllArgsConstructor
public class TransportationJpaFindByParamAdapter implements FindByParamsTransportationServiceOutputPort {

    @PersistenceContext
    private final EntityManager entityManager;

    private final EdgeRepository edgeRepository;

    private final RouteRepository repository;

    private final JdbcTemplate jdbcTemplate;

    private final RouteStepRepository routeStepRepository;

    @Override
    @Transactional
    @Cacheable(key = "#entity.hashCode() % #pageEntity.hashCode()", value = "TransportationJpaFindByParamAdapter::findBy")
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {

        List<String> routeIds = null;
        List<Route> routesByIdIn = null;
        if (entity.getRouteId() == null || entity.getRouteId().isEmpty()) {
            List<RouteEntityTemp> recursiveResults = repository.findRecursiveRoutes(entity.getFrom(), entity.getTo(), entity.getType(), pageEntity.getPageSize(), pageEntity.getPageNum() * pageEntity.getPageSize());
            Deque<String> idsQueue = new LinkedList<>();
            for (int i = 0; i < recursiveResults.size(); i++) {
                idsQueue.push(UUID.nameUUIDFromBytes(recursiveResults.get(i).toString().getBytes()).toString());
            }

            routesByIdIn = repository.findRoutesByIdIn(idsQueue);
            for (var i : routesByIdIn) {
                idsQueue.remove(i.getId());
            }

            if (!idsQueue.isEmpty()) {
                List<String> ids = new ArrayList<>();
                recursiveResults.forEach(row -> {
                    Collections.addAll(ids, row.path());
                });
                var a = edgeRepository.findAllByIdIn(ids);
                Map<Long, Edge> idsEl = new HashMap<>();

                for (var j : a) {
                    if (!idsEl.containsKey(j.getId())) {
                        idsEl.put(j.getId(), j);
                    }
                }
                List<Route> routes = new ArrayList<>();

                for (var rec : recursiveResults) {
                    if (idsQueue.isEmpty()) {
                        break;
                    }
                    Route route = new Route();
                    route.setId(idsQueue.poll());
                    int i = 1;
                    for (var j : rec.path()) {
                        RouteStep routeStep = new RouteStep(i++, idsEl.get(Long.parseLong(j)));
                        route.add(routeStep);
                    }
                    route.setArrivalTime(rec.getArrivTime());
                    route.setDepartureTime(rec.depTime());
                    route.setDepartureCity(idsEl.get(Long.parseLong(rec.path()[0])).getFromLocationId());
                    route.setArrivalCity(idsEl.get(Long.parseLong(rec.path()[rec.path().length - 1])).getToLocationId());
                    routes.add(route);
                }


                jdbcTemplate
                        .batchUpdate("INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                Route route = routes.get(i);
                                ps.setString(1, route.getId());
                                ps.setString(2, route.getDepartureCity().getId());
                                ps.setString(3, route.getArrivalCity().getId());
                                ps.setTimestamp(4, Timestamp.valueOf(route.getDepartureTime()));
                                ps.setTimestamp(5, Timestamp.valueOf(route.getArrivalTime()));
                            }

                            @Override
                            public int getBatchSize() {
                                return routes.size();
                            }
                        });
                List<RouteStep> steps = new ArrayList<>();
                for (var i : routes) {
                    steps.addAll(i.getRouteSteps());
                }

                jdbcTemplate.batchUpdate("INSERT INTO t_route_step (route_id, route_step, edge_id) " +
                        "VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        RouteStep step = steps.get(i);
                        ps.setString(1, step.getRoute().getId());
                        ps.setInt(2, step.getRouteStep());
                        ps.setLong(3, step.getEdgeId().getId());
                    }
                    @Override
                    public int getBatchSize() {
                        return steps.size();
                    }
                });


                routesByIdIn.addAll(routes);
            }
        } else {
            routeIds = entity.getRouteId();
            routesByIdIn = repository.findRoutesByIdIn(routeIds);
        }


        return RouteMapper.INSTANCE.routesToRouteEntitys(routesByIdIn);
    }
}
