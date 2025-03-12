package org.service.output_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RouteStepId.class)
@Table(name = "t_route_step")
public class RouteStep {

    @Id
    @Column(name = "route_id")
    private String routeId;

    @Id
    @Column(name = "route_step")
    private Integer routeStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edge_id", referencedColumnName = "edge_id")
    private Edge edgeId;


}
