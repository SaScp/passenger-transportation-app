package org.service.output_port.jdbc.adapter;

import org.service.entity.RoutesEntity;
import org.service.output_port.factory.RouteFactory;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractFindRoutesAdapter extends MappingSqlQuery<RoutesEntity> {


    public AbstractFindRoutesAdapter(DataSource ds, String sql) {
        super(ds, sql);
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.createRoute(rs);
    }
}
