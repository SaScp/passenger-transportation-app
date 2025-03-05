package org.service.passangertransportationgraphjpaadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteEdgeDto {

    private String id;

    private String from;

    private String to;

    @JsonProperty("route_id")
    private String routeId;


    public static RouteEdgeDto createRouteEdge(String id, String from, String to, String edgeId) {
        return new RouteEdgeDto(String.format("%s - %s", id, edgeId), from, to, edgeId);
    }
}
