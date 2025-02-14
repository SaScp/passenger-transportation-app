package org.service.output_port.jdbc;

import org.service.entity.RoutesEntity;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.SQLConstant;
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
        super(ds, SQLConstant.SELECT_ALL_ROUTES);
    }

    @Override
    public List<RoutesEntity> findAll() {
        return this.execute();
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.routeFactory(rs);
    }


}
