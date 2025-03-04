package org.service.passangertransportationgraphjpaadapter.repository;

import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {


    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId"})
    List<Route> findRoutesByDepartureCityId(String id);
}
