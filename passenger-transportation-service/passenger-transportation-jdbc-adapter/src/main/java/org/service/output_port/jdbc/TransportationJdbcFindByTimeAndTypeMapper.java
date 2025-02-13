package org.service.output_port.jdbc;

import org.service.entity.RoutesEntity;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

@Component
public class TransportationJdbcFindByTimeAndTypeMapper extends MappingSqlQuery implements FindByParamsTransportationServiceOutputPort, TransportationJdbcAdapter {

    public TransportationJdbcFindByTimeAndTypeMapper(DataSource ds) {
        super(ds, """
                                SELECT *
                                FROM routes
                                WHERE departure_city = ?
                                  AND arrival_city = ?
                                  AND transport_type = ?
                                  AND departure_time >= ?
                                ORDER BY departure_time ASC;
                """);
    }

    @Override
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }

    @Override
    public Iterable<RoutesEntity> findBy(ZonedDateTime time, String type) {
        return null;
    }
}
