package org.service.output_port.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity

@NoArgsConstructor
@Table(name = "t_route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_city", referencedColumnName = "id", nullable = false)
    private Location departureCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_city", referencedColumnName = "id", nullable = false)
    private Location arrivalCity;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST},  orphanRemoval = true)
    private List<RouteStep> routeSteps = new ArrayList<>();

    public Route(String id, Location departureCity, Location arrivalCity, LocalDateTime departureTime, LocalDateTime arrivalTime, List<RouteStep> routeSteps) {
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.routeSteps = routeSteps;
    }

    public Route(Location departureCity, Location arrivalCity, LocalDateTime departureTime, LocalDateTime arrivalTime, List<RouteStep> routeSteps) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.routeSteps = routeSteps;
    }

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

    public void add(RouteStep routeStep) {
        routeStep.setRoute(this);
        routeSteps.add(routeStep);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, departureCity, arrivalCity, departureTime, arrivalTime, routeSteps);
    }
}