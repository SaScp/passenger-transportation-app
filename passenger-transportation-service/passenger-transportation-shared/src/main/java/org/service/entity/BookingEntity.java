package org.service.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public record BookingEntity(
        String id,
        String bookingTime,
        String userPhone,
        String status,
        String route
) implements Serializable {
    @Override
    public int hashCode() {
        return Objects.hash(id, bookingTime, userPhone, status, route);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(route, that.route) && Objects.equals(status, that.status) && Objects.equals(userPhone, that.userPhone) && Objects.equals(bookingTime, that.bookingTime);
    }
}
