package org.service.output_port.filter_handler;

public class SQLConstant {

    public static final String START_SELECT_BY_PARAMS_QUERY = "SELECT routes.id,routes.departure_city , routes.arrival_city,routes.departure_time, routes.arrival_time,type_name, routes.price  FROM routes INNER JOIN main.transport_types tt on routes.transport_type_id = tt.id WHERE ";

    public static final String END_SELECT_BY_PARAMS_QUERY = "LIMIT 5;";

    public static final String WHERE = "WHERE ";
}
