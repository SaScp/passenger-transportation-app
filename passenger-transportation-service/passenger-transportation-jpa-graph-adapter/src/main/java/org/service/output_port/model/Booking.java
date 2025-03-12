package org.service.output_port.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_booking")
public class Booking {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_phone", referencedColumnName = "user_phone")
    private User userPhone;

    @Column(name = "route_id")
    private String route;

}
