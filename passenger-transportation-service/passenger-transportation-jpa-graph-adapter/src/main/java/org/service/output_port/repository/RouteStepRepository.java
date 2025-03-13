package org.service.output_port.repository;


import org.service.output_port.model.RouteStep;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface RouteStepRepository extends JpaRepository<RouteStep, Long> {

    @EntityGraph(attributePaths = {"edgeId.fromLocationId","edgeId.toLocationId", "edgeId.type"})
    List<RouteStep> findRouteStepsByRouteIdIn(Collection<String> id);

    @Override
    @EntityGraph(attributePaths = {"edgeId.fromLocationId","edgeId.toLocationId", "edgeId.type"})
    List<RouteStep> findAll();
}
