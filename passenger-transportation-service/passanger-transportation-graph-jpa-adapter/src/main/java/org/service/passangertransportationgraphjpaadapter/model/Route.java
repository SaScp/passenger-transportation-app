package org.service.passangertransportationgraphjpaadapter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_route")
public class Route {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "departure_city", referencedColumnName = "id", nullable = false)
    private Location departureCity;

    @ManyToOne
    @JoinColumn(name = "arrival_city", referencedColumnName = "id", nullable = false)
    private Location arrivalCity;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @OneToMany
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
    private List<RouteStep> routeSteps;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route route)) return false;
        return Objects.equals(id, route.id) &&
                Objects.equals(departureCity, route.departureCity) &&
                Objects.equals(arrivalCity, route.arrivalCity) &&
                Objects.equals(departureTime, route.departureTime) &&
                Objects.equals(arrivalTime, route.arrivalTime) &&
                Objects.equals(routeSteps, route.routeSteps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureCity, arrivalCity, departureTime, arrivalTime, routeSteps);
    }
}