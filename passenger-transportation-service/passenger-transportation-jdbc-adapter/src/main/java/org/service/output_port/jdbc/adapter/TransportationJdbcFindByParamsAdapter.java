package org.service.output_port.jdbc.adapter;

import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.Result;
import org.service.entity.RoutesEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.find.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.JdbcLruIdCache;
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


public class TransportationJdbcFindByParamsAdapter extends AbstractFindRoutesAdapter implements FindByParamsTransportationServiceOutputPort, TransportationJdbcAdapter {

    private static final Logger log = LoggerFactory.getLogger(TransportationJdbcFindByParamsAdapter.class);

    private HandlerExecutor handler;

    private final JdbcLruIdCache<Result, List<RoutesEntity>> lruIdCache;

    public TransportationJdbcFindByParamsAdapter(DataSource ds, JdbcLruIdCache<Result, List<RoutesEntity>> cache) {
        super(ds, "");
        this.lruIdCache = cache;
        this.handler = new HandlerExecutor();
    }


    @Override
    @Transactional
    public List<RoutesEntity> findBy(ParamsEntity entity, PageEntity pageEntity) {
        Result resultVal = this.handler.execute(entity);
        resultVal.params().add(pageEntity.getPageSize());
        resultVal.params().add(pageEntity.getPageNum() * pageEntity.getPageSize());
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
                throw new ProblemDetailsException(500, "sql error");
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
            if (param instanceof String str)
                query.setString(indexParam++, str);
            else if(param instanceof Integer integer)
                query.setInt(indexParam++, integer);
        }
    }


}
