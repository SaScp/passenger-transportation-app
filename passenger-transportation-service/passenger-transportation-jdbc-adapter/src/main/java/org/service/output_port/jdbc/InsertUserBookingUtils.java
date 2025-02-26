package org.service.output_port.jdbc;

import org.service.exception.ProblemDetailsException;
import org.service.output_port.filter_handler.SQLConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InsertUserBookingUtils extends UserBookingUtils {

    private static final Logger log = LoggerFactory.getLogger(InsertUserBookingUtils.class);

    public InsertUserBookingUtils(Connection e) throws SQLException {
        super(e);
    }

    private boolean selectUser(String numberPhone) throws SQLException {
        try (PreparedStatement selectUser = connection.prepareStatement(SQLConstant.USER_EXISTS)){
            selectUser.setString(1, numberPhone);
            try (ResultSet rs = selectUser.executeQuery()) {
                if (rs.getInt(1) == 0) {
                  return true;
                }
            }
        } catch (Exception e) {
            log.error("error in method {} message {}", e.getStackTrace()[1], e.getMessage());
            throw new SQLException();
        }
        return false;
    }

    public void insertUser(String numberPhone) throws SQLException {
        try (PreparedStatement insertUser = connection.prepareStatement(SQLConstant.USER_USER_PHONE_VALUES)){
            if (selectUser(numberPhone)) {
                insertUser.setString(1, numberPhone);
                insertUser.execute();
            }
        }
    }
}
