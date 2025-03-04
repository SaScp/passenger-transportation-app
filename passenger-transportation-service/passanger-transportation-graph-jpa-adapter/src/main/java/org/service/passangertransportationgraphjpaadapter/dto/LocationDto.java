package org.service.passangertransportationgraphjpaadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.service.passangertransportationgraphjpaadapter.model.Location;

import java.io.Serializable;

/**
 * DTO for {@link Location}
 */

@AllArgsConstructor
@Getter
@Setter
public class LocationDto {

    private String id;

    private String label;

    public static LocationDto fromLocation(Location location) {
        return new LocationDto(location.getId(), location.getCName());
    }
}