package org.service.output_port.jdbc;

import org.service.entity.RoutesEntity;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransportationJdbcFindAllMapper extends MappingSqlQuery implements FindAllTransportationServiceOutputPort, TransportationJdbcAdapter {
    public TransportationJdbcFindAllMapper(DataSource ds) {
        super(ds, "");
    }

    @Override
    public Iterable<RoutesEntity> findAll() {
        return null;
    }

    @Override
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
