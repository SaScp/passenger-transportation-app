package org.service.output_port.jdbc.adapter;

import org.service.entity.ParamsEntity;
import org.service.entity.Result;
import org.service.entity.RoutesEntity;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.LruIdCache;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TransportationJdbcFindByParamsAdapter extends MappingSqlQuery<RoutesEntity> implements FindByParamsTransportationServiceOutputPort, TransportationJdbcAdapter {

    private static final Logger log = LoggerFactory.getLogger(TransportationJdbcFindByParamsAdapter.class);

    private HandlerExecutor handler;

    private final LruIdCache<Result, List<RoutesEntity>> lruIdCache;

    public TransportationJdbcFindByParamsAdapter(DataSource ds, LruIdCache<Result, List<RoutesEntity>> cache) {
        super(ds, "");
        this.lruIdCache = new LruIdCache<>(20);
        this.handler = new HandlerExecutor();
    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.createRoute(rs);
    }

    @Override
    @Transactional
    public List<RoutesEntity> findBy(ParamsEntity entity) {
        Result resultVal = this.handler.execute(entity);

        return getEntities(resultVal);
    }

    private List<RoutesEntity> getEntities(Result resultVal) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (lruIdCache.containsKey(resultVal)) {
            return lruIdCache.get(resultVal);
        }
        return jdbcTemplate.execute((ConnectionCallback<List<RoutesEntity>>) e -> {
            try (PreparedStatement query = e.prepareStatement(resultVal.query())) {
                generateParams(resultVal, query);

                List<RoutesEntity> routesEntities = createRouteList(query);
                lruIdCache.put(resultVal, routesEntities);
                return routesEntities;
            } catch (SQLException ex) {
                log.error("error in method {} message {}", ex.getStackTrace()[1], ex.getMessage());
                throw new RuntimeException();
            }
        });
    }

    private List<RoutesEntity> createRouteList(PreparedStatement query) throws SQLException {
        List<RoutesEntity> routesEntities = new ArrayList<>();
        ResultSet resultSet = query.executeQuery();
        while (resultSet.next()) {
            routesEntities.add(mapRow(resultSet, 0));
        }
        return routesEntities;
    }

    private void generateParams(Result resultVal, PreparedStatement query) throws SQLException {
        int indexParam = 1;
        for (var param : resultVal.params()) {
            query.setString(indexParam++, param);
        }
    }


}
