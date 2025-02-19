package org.service.entity;

import java.sql.Timestamp;

public record BookingEntity(
        String id,
        String bookingTime,
        String userPhone,
        String status,
        String route
) { }
