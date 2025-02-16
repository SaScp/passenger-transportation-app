package org.service.output_port.jdbc;

import org.service.entity.RoutesEntity;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.SQLConstant;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class TransportationJdbcFindAllMapper extends MappingSqlQuery<RoutesEntity> implements FindAllTransportationServiceOutputPort, TransportationJdbcAdapter {
    public TransportationJdbcFindAllMapper(DataSource ds) {
        super(ds, SQLConstant.SELECT_ALL_ROUTES);
    }

    @Override
    @Transactional
    public List<RoutesEntity> findAll() {
        return this.execute();
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.createRoute(rs);
    }


}
