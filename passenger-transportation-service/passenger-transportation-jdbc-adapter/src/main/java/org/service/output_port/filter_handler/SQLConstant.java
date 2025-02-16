package org.service.output_port.filter_handler;

public class SQLConstant {

    public static final String START_SELECT_BY_PARAMS_QUERY = "SELECT t_routes.id,t_routes.departure_city , t_routes.arrival_city, t_routes.departure_time, t_routes.arrival_time,type_name, t_routes.price  FROM t_routes INNER JOIN t_transport_types tt on t_routes.transport_type_id = tt.id WHERE ";

    public static final String END_SELECT_BY_PARAMS_QUERY = "ORDER BY departure_time ASC LIMIT 5;";

    public static final String WHERE = "WHERE ";

    public static final String SELECT_ALL_ROUTES = """
                    SELECT t_routes.id, departure_city, arrival_city, departure_time, arrival_time, type_name, price  FROM t_routes
                    INNER JOIN t_transport_types
                        ON  t_routes.transport_type_id = t_transport_types.id  ORDER BY departure_time ASC""";

    public static final String SELECT_ALL_BOOKING_BY_PHONE = """
            SELECT\s
                tb.id,\s
                tb.booking_time,\s
                tu.user_phone,\s
                ts.status,\s
                tr.departure_city,\s
                tr.arrival_city,\s
                tr.departure_time,\s
                tr.arrival_time,\s
                tt.type_name,\s
                tr.price
            FROM t_bookings tb
            INNER JOIN t_user_bookings tub ON tb.id = tub.booking_id
            INNER JOIN t_user tu ON tub.user_phone = tu.user_phone
            INNER JOIN t_status ts ON ts.id = tb.status_id
            INNER JOIN t_routes tr ON tr.id = tb.route_id
            INNER JOIN t_transport_types tt ON tr.transport_type_id = tt.id
            WHERE tu.user_phone = ?;
            """;
}
