package org.service.output_purt.repository;


import org.service.output_purt.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @Query("select route.id from Route route where route.departureCity.id = :id")
    List<String> findRoutesIdByDepId(@Param("id") String id, Pageable pageable);

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId","routeSteps.edgeId.fromLocationId"})
    List<Route> findRoutesByIdIn(List<String> ids);

    @Override
    @EntityGraph(attributePaths = {"departureCity", "arrivalCity"})
    Page<Route> findAll(Pageable pageable);

    @Query("select route.id from Route route")
    Page<String> findIds(Pageable pageable);

}
