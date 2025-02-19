package org.service.ouptput_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_bookings")
public class Booking {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_phone", referencedColumnName = "user_phone")
    private User userPhone;

    @Column(name = "route_id")
    private String route;

}
