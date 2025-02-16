package org.service.output_port.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertUserBookingUtils extends UserBookingUtils {

    public InsertUserBookingUtils(Connection e) throws SQLException {
        super(e);
    }
    private boolean selectUser(String numberPhone) throws SQLException {
        try (PreparedStatement selectUser = connection.prepareStatement("SELECT exists(SELECT user_phone FROM t_user WHERE user_phone = ?)")){
            selectUser.setString(1, numberPhone);
            try (ResultSet rs = selectUser.executeQuery()) {
                if (rs.getInt(1) == 0) {
                  return true;
                }
            }
        }
        return false;
    }

    public void insertUser(String numberPhone) throws SQLException {
        try (PreparedStatement insertUser = connection.prepareStatement("INSERT INTO t_user(user_phone) VALUES (?)")){
            if (selectUser(numberPhone)) {
                insertUser.setString(1, numberPhone);
                insertUser.execute();
            }
        }
    }

    public void insertUserBooking(String numberPhone, String bookingId) throws SQLException {
        try (PreparedStatement insertUserBooking = connection.prepareStatement("INSERT INTO t_user_bookings(user_phone, booking_id) VALUES (?,?)")){
            insertUserBooking.setString(1, numberPhone);
            insertUserBooking.setString(2, bookingId);
            insertUserBooking.execute();
        } catch (Exception e) {
            throw new RuntimeException("User with book already exists");
        }
    }
}
