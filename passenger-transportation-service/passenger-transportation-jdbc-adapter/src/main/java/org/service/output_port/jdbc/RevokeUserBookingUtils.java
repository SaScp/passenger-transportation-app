package org.service.output_port.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class RevokeUserBookingUtils extends UserBookingUtils {

    public RevokeUserBookingUtils(Connection e) throws SQLException {
        super(e);
    }


}
