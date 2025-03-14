package org.service.output_port.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_location_id", referencedColumnName = "id")
    private Location fromLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location_id", referencedColumnName = "id")
    private Location toLocationId;

    @Column(name = "time_cost", nullable = false)
    private Long timeCost;

    @Column(name = "price")
    private Double price;

    @Column(name = "type_id",  insertable = false, updatable = false)
    private Integer cType;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;
}