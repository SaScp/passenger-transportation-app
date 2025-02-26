package org.service.ouptput_port.repository;

import org.service.ouptput_port.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @EntityGraph(attributePaths = {"transportType"})
    Page<Route> findAll(Pageable pageable);
}
