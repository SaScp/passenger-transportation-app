package org.service.output_port.jdbc;

import org.service.entity.RoutesEntity;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.service.output_port.TransportationServiceOutputPort;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Component
public class TransportationJdbcFindAllMapper extends MappingSqlQuery<RoutesEntity> implements FindAllTransportationServiceOutputPort, TransportationJdbcAdapter {
    public TransportationJdbcFindAllMapper(DataSource ds) {
        super(ds, """
                    SELECT routes.id, departure_city, arrival_city, departure_time, arrival_time, type_name, price  FROM routes
                    INNER JOIN transport_types
                        ON  routes.transport_type_id = transport_types.id  ORDER BY departure_time ASC
                """);
    }

    @Override
    public List<RoutesEntity> findAll() {
        return this.execute();
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
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
