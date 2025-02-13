package org.service.output_port.jdbc;

import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.*;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Component
public class TransportationJdbcFindByParamsMapper extends MappingSqlQuery<RoutesEntity> implements FindByParamsTransportationServiceOutputPort, TransportationJdbcAdapter {

    private TypeParamHandler parentTypeHandler;
    public TransportationJdbcFindByParamsMapper(DataSource ds) {
        super(ds, "");
        ToParamHandler toParamHandler = new ToParamHandler();
        FromParamHandler fromParamHandler = new FromParamHandler();
        TimeParamHandler timeParamHandler = new TimeParamHandler();
        parentTypeHandler = new TypeParamHandler();
        parentTypeHandler.nextNode(toParamHandler);
        toParamHandler.nextNode(fromParamHandler);
        fromParamHandler.nextNode(timeParamHandler);



    }

    @Override
    protected RoutesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RouteFactory.routeFactory(rs);
    }

    @Override
    public List<RoutesEntity> findBy(ParamsEntity entity) {
        this.setSql(this.parentTypeHandler.bulid());
        this.parentTypeHandler.queryClear();

        return null;
    }

    private void generateDecParam(Map<String, String> countParams) {
        this.getDeclaredParameters().clear();
        for (var i : countParams.keySet()) {
            this.getDeclaredParameters().add(new SqlParameter(Types.VARCHAR, i));
        }
    }


}
