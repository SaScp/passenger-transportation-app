package org.service.passangertransportationgraphjpaadapter.service;


import lombok.AllArgsConstructor;
import org.service.passangertransportationgraphjpaadapter.dto.LocationDto;
import org.service.passangertransportationgraphjpaadapter.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    @Override
    public List<LocationDto> getLocations() {
        return repository.findAll().stream().map(LocationDto::fromLocation).toList();
    }
}
