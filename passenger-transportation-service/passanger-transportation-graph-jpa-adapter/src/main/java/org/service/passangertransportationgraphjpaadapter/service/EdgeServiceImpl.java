package org.service.passangertransportationgraphjpaadapter.service;

import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.EdgeDto;
import org.service.passangertransportationgraphjpaadapter.dto.Graph;
import org.service.passangertransportationgraphjpaadapter.repository.EdgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EdgeServiceImpl implements EdgeService {

    private final EdgeRepository edgeRepository;


    @Override
    public List<Graph> getEdges() {
        return List.of();
    }
}
