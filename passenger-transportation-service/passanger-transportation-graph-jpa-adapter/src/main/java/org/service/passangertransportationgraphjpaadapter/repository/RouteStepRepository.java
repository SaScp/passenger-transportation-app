package org.service.passangertransportationgraphjpaadapter.repository;

import org.service.passangertransportationgraphjpaadapter.model.RouteStep;
import org.service.passangertransportationgraphjpaadapter.model.RouteStepId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RouteStepRepository extends JpaRepository<RouteStep, RouteStepId> {

    @EntityGraph(attributePaths = {"edgeId.fromLocationId","edgeId.toLocationId"})
    List<RouteStep> findRouteStepsByRouteIdIn(List<String> ids);

    @Override
    @EntityGraph(attributePaths = {"edgeId.fromLocationId","edgeId.toLocationId"})
    List<RouteStep> findAll();
}
