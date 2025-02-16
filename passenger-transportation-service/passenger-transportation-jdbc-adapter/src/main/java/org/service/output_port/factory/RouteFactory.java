package org.service.output_port.factory;

import org.service.entity.RoutesEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteFactory {
    public static RoutesEntity createRoute(ResultSet rs) throws SQLException {
        return new RoutesEntity(
                rs.getString("id"),
                rs.getString("departure_city"),
                rs.getString("arrival_city"),
                rs.getString("departure_time"),
                rs.getString("arrival_time"),
                rs.getString("type_name"),
                rs.getInt("price")
        );
    }
}
