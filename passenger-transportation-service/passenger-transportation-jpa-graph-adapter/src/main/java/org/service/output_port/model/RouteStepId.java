package org.service.output_port.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class RouteStepId implements Serializable {

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "route_step")
    private Integer routeStep;

    public RouteStepId() {
    }

    public RouteStepId(String routeId, Integer routeStep) {
        this.routeId = routeId;
        this.routeStep = routeStep;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteStepId)) return false;
        RouteStepId that = (RouteStepId) o;
        return Objects.equals(routeId, that.routeId) &&
                Objects.equals(routeStep, that.routeStep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, routeStep);
    }
}
