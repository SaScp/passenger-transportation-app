package org.service.output_port.util;

import org.service.output_port.model.Route;
import org.service.output_port.model.RouteStep;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class BatchUtils {

    private final JdbcTemplate jdbcTemplate;


    public BatchUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executeSaveAll(List<Route> routes) {

            executeBatchRoutesInsert(routes);
            List<RouteStep> steps = new ArrayList<>();
            for (var route : routes) {
                steps.addAll(route.getRouteSteps());
            }
            executeBatchRouteStepInsert(steps);


    }

    private void executeBatchRouteStepInsert(List<RouteStep> steps) {
        jdbcTemplate.batchUpdate("INSERT INTO t_route_step (route_id, route_step, edge_id) " +
                "VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RouteStep step = steps.get(i);
                ps.setString(1, step.getRoute().getId());
                ps.setInt(2, step.getRouteStep());
                ps.setLong(3, step.getEdgeId().getId());
            }
            @Override
            public int getBatchSize() {
                return steps.size();
            }
        });
    }

    private void executeBatchRoutesInsert(List<Route> routes) {
        jdbcTemplate.batchUpdate("INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Route route = routes.get(i);
                        ps.setString(1, route.getId());
                        ps.setString(2, route.getDepartureCity().getId());
                        ps.setString(3, route.getArrivalCity().getId());
                        ps.setTimestamp(4, Timestamp.valueOf(route.getDepartureTime()));
                        ps.setTimestamp(5, Timestamp.valueOf(route.getArrivalTime()));
                    }

                    @Override
                    public int getBatchSize() {
                        return routes.size();
                    }
                });
    }
}
