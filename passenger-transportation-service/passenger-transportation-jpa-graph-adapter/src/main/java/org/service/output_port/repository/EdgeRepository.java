package org.service.output_port.repository;


import org.service.output_port.model.Edge;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {

    @EntityGraph(attributePaths = {"fromLocationId", "toLocationId", "type"})
    List<Edge> findAllByIdIn(List<String> ids);

    @Override
    @EntityGraph(attributePaths = {"fromLocationId", "toLocationId", "type"})
    List<Edge> findAll();
}
