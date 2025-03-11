package org.service.output_purt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_location_graph")
@NamedEntityGraph(
        name = "Edge.withCType",
        attributeNodes = @NamedAttributeNode("cType")
)
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


    @Column(name = "type_id")
    private Integer cType;
}