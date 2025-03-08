package org.service.output_purt.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class RouteStepId implements Serializable {

    private String routeId;
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
