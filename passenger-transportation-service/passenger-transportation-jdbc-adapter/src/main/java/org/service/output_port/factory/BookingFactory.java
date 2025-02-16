package org.service.output_port.factory;

import org.service.entity.BookingEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingFactory {

    public static BookingEntity createBooking(ResultSet rs) throws SQLException {
        return new BookingEntity(
                rs.getString("id"),
                rs.getString("booking_time"),
                rs.getString("user_phone"),
                rs.getString("status"),
                rs.getString("departure_city"),
                rs.getString("arrival_city"),
                rs.getTimestamp("departure_time"),
                rs.getTimestamp("arrival_time"),
                rs.getString("type_name"),
                rs.getDouble("price")
        );
    }
}
