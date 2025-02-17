package org.service.ouptput_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Entity
@Getter
@Setter
@Table(name = "t_routes")
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(name = "t_transport_types", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "type_name", table = "t_transport_types")
    private String typeName;

    @Column(name = "price")
    private int price;

}
