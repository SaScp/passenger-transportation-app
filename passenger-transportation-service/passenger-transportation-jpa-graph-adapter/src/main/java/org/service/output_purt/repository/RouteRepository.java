package org.service.output_purt.repository;


import org.service.output_purt.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId","routeSteps.edgeId.fromLocationId"})
    List<Route> findRoutesByDepartureCityId(String id);
}
