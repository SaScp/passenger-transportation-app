package org.service.entity;

import java.sql.Timestamp;

public record BookingEntity(
        String id,
        String bookingTime,
        String userPhone,
        String status,
        String departureCity,
        String arrivalCity,
        Timestamp departureTime,
        Timestamp arrivalTime,
        String typeName,
        double price
) { }
