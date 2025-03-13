package org.service.output_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "t_route_step")
public class RouteStep {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "route_step")
    private Integer routeStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edge_id", referencedColumnName = "edge_id")
    private Edge edgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;


    public RouteStep(Integer routeStep, Edge edge) {
        this.routeStep = routeStep;
        this.edgeId = edge;
    }


}
