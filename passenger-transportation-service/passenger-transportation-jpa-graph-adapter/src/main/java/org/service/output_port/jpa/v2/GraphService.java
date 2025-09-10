package org.service.output_port.jpa.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.repository.EdgeRepository;
import org.service.output_port.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GraphService {
    private final EdgeRepository edgeRepository;
    private final LocationRepository locationRepository;
}
