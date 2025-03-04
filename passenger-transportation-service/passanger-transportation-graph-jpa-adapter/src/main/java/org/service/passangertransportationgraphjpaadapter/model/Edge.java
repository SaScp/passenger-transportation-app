package org.service.passangertransportationgraphjpaadapter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_location_graph")
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "edge_id", nullable = false)
    private Long id;

    @Column(name = "from_location_id")
    private String fromLocationId;

    @Column(name = "to_location_id")
    private String toLocationId;

    @Column(name = "time_cost", nullable = false)
    private Long timeCost;

    @Column(name = "price")
    private Double price;

    @Column(name = "type_id")
    private Integer cType;
}