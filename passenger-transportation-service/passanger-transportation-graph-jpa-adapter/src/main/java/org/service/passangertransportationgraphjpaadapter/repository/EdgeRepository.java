package org.service.passangertransportationgraphjpaadapter.repository;

import org.service.passangertransportationgraphjpaadapter.model.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {

}
