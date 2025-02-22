package org.service.ouptput_port.repository;

import org.service.ouptput_port.model.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TransportType, Long> {
}
