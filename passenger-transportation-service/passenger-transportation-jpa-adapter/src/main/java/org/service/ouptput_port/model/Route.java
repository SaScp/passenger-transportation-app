package org.service.ouptput_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Cacheable
@Table(name = "t_routes")
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_type_id", referencedColumnName = "id")
    private TransportType transportType;

    @Column(name = "price")
    private int price;


}
