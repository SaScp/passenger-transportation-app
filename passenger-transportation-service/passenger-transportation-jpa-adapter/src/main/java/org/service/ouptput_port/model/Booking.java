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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_bookings")
@SecondaryTable(name = "t_status", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Booking {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "status", table = "t_status")
    private String status;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;
}
