package org.service.output_port.jdbc;

import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.RevokeBookingTransportationServiceOutputPort;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class TransportationJdbcRevokeBookingMapper  extends UpdatableSqlQuery implements RevokeBookingTransportationServiceOutputPort, TransportationJdbcAdapter {
    public TransportationJdbcRevokeBookingMapper(DataSource ds) {
        super(ds, "");
    }

    @Override
    public boolean revoke(Void tt) {
        return false;
    }


    @Override
    protected Object updateRow(ResultSet rs, int rowNum, Map context) throws SQLException {
        return null;
    }

}
