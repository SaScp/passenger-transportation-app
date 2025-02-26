package org.service.output_port.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class UserBookingUtils {

    protected final Connection connection;

    public UserBookingUtils(Connection e) throws SQLException {
        this.connection = e;

    }
}
