package org.service.output_port.repository;


import org.antlr.v4.runtime.misc.NotNull;
import org.service.output_port.model.Route;
import org.service.output_port.model.RoutePageEntity;
import org.service.output_port.util.RouteSQLConstaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId", "routeSteps.edgeId.fromLocationId", "routeSteps.edgeId.type"})
    List<Route> findRoutesByIdIn(List<String> ids, Sort sort);

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId", "routeSteps.edgeId.fromLocationId", "routeSteps.edgeId.type"})
    List<Route> findRoutesByIdIn(Collection<String> ids);

    @Override
    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps"})
    Page<Route> findAll(Pageable pageable);

    @Query("select route.id from Route route")
    Page<String> findIds(Pageable pageable);


    @Query(value = RouteSQLConstaint.SELECT_ALL_ROUTE_BY_PARAM_WITH_DFS, nativeQuery = true)
    List<RoutePageEntity> findRecursiveRoutes(
            @Param("fromLocation") String fromLocation,
            @Param("toLocation") String toLocation,
            @NotNull @Param("type") String type,
            @Param("depTime") String depTime,
            @Param("limit") int limit,
            @Param("offset") int offset);


    @Query(value = RouteSQLConstaint.SELECT_ALL_ROUTE_WITH_DFS, nativeQuery = true)
    List<RoutePageEntity> findAllRecursiveRoutesById(
            @Param("fromLocation") String fromLocation,
            @Param("limit") int limit,
            @Param("offset") int offset);
}
