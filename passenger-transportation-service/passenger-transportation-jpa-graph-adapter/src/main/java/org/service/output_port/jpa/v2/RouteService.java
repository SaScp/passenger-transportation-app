package org.service.output_port.jpa.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.repository.RouteRepository;
import org.service.output_port.repository.TypeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final TypeRepository typeRepository;
}
