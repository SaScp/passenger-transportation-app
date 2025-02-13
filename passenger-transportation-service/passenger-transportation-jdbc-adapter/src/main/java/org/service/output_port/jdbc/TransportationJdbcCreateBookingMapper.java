package org.service.output_port.jdbc;

import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class TransportationJdbcCreateBookingMapper extends UpdatableSqlQuery implements CreateBookingTransportationServiceOutputPort, TransportationJdbcAdapter {

    public TransportationJdbcCreateBookingMapper(DataSource ds) {
        super(ds, "");
    }

    @Override
    public boolean create(Void tt) {
        return false;
    }

    @Override
    protected Object updateRow(ResultSet rs, int rowNum, Map context) throws SQLException {
        return null;
    }
}
