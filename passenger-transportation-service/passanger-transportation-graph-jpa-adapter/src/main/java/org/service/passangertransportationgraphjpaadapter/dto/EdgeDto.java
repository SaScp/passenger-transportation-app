package org.service.passangertransportationgraphjpaadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.service.passangertransportationgraphjpaadapter.model.Edge;

import java.io.Serializable;

/**
 * DTO for {@link Edge}
 */
@Value
@AllArgsConstructor
public class EdgeDto implements Serializable {

    @JsonProperty("from")
    String fromLocationId;

    @JsonProperty("to")
    String toLocationId;


    public static EdgeDto fromEdge(Edge edge) {
        return new EdgeDto(edge.getFromLocationId(), edge.getToLocationId());
    }
}