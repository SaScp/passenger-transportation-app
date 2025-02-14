package org.service.output_port.jdbc;

import org.service.entity.ParamsEntity;
import org.service.entity.Result;
import org.service.entity.RoutesEntity;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.*;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TransportationJdbcFindByParamsMapper extends MappingSqlQuery<RoutesEntity> implements FindByParamsTransportationServiceOutputPort, TransportationJdbcAdapter {

    private HandlerExecutor handler;

    public TransportationJdbcFindByParamsMapper(DataSource ds) {
        super(ds, "");

        this.handler = new HandlerExecutor();
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.routeFactory(rs);
    }

    @Override
    public List<RoutesEntity> findBy(ParamsEntity entity) {
        Result resultVal = this.handler.execute(entity);
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        List<RoutesEntity> result = jdbcTemplate.execute((ConnectionCallback<List<RoutesEntity>>) e -> {
            try (PreparedStatement query = e.prepareStatement(resultVal.query())) {
                List<RoutesEntity> routesEntities = new ArrayList<>();

                int indexParam = 1;
                for (var param : resultVal.params()) {
                    query.setString(indexParam++, param);
                }

                ResultSet resultSet = query.executeQuery();
                while (resultSet.next()) {
                    routesEntities.add(mapRow(resultSet, 0));
                }

                return routesEntities;
            } catch (SQLException ex) {
                throw new RuntimeException();
            }
        });


        return result;
    }


}
