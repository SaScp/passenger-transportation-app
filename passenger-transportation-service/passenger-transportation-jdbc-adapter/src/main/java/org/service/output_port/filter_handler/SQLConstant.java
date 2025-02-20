package org.service.output_port.filter_handler;

public class SQLConstant {

    public static final String START_SELECT_BY_PARAMS_QUERY = "SELECT t_routes.id,t_routes.departure_city , t_routes.arrival_city, t_routes.departure_time, t_routes.arrival_time,type_name, t_routes.price  FROM t_routes INNER JOIN t_transport_types tt on t_routes.transport_type_id = tt.id WHERE ";

    public static final String END_SELECT_BY_PARAMS_QUERY = "ORDER BY departure_time ASC LIMIT 5;";

    public static final String WHERE = "WHERE ";

    public static final String SELECT_ALL_ROUTES = """
            SELECT t_routes.id, departure_city, arrival_city, departure_time, arrival_time, type_name, price  FROM t_routes
            INNER JOIN t_transport_types
                ON  t_routes.transport_type_id = t_transport_types.id  ORDER BY departure_time ASC limit ? offset ?""";

    public static final String SELECT_ALL_BOOKING_BY_PHONE = """
            SELECT
                                                                           tb.id,
                                                                           tb.booking_time,
                                                                           tb.user_phone,
                                                                           ts.status,
                                                                           tb.route_id
                                                                       FROM t_bookings tb
                                                                                INNER JOIN t_status ts ON ts.id = tb.status_id
                                                                       WHERE tb.user_phone = ?;
            """;

    public static final String SELECT_BOOKING_BY_ID = "SELECT t_bookings.user_phone FROM t_bookings WHERE id = ?";

    public static final String UPDATE_STATUS = "UPDATE t_bookings SET status_id = ? WHERE id = ?";

    public static final String INSERT_BOOKING = "INSERT INTO t_bookings(id, route_id, booking_time, status_id, user_phone) VALUES (?,?,?,?,?);";

    public static final String USER_USER_PHONE_VALUES = "INSERT INTO t_user(user_phone) VALUES (?)";

    public static final String USER_EXISTS = "SELECT exists(SELECT user_phone FROM t_user WHERE user_phone = ?)";
}
