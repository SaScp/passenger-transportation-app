package org.service.passangertransportationgraphjpaadapter.repository;

import org.service.passangertransportationgraphjpaadapter.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}
