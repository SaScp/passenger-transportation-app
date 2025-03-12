package org.service.output_port.jdbc.adapter;

import org.service.entity.PageEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.find.FindAllTransportationServiceOutputPort;
import org.service.output_port.factory.RouteFactory;
import org.service.output_port.filter_handler.SQLConstant;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


public class TransportationJdbcFindAllAdapter extends AbstractFindRoutesAdapter implements FindAllTransportationServiceOutputPort, TransportationJdbcAdapter {
    public TransportationJdbcFindAllAdapter(DataSource ds) {
        super(ds, SQLConstant.SELECT_ALL_ROUTES);
        this.declareParameter(new SqlParameter(Types.INTEGER));
        this.declareParameter(new SqlParameter(Types.INTEGER));
    }

    @Override
    @Transactional
    public List<RoutesEntity> findAll(PageEntity pageEntity) {
        return this.execute(pageEntity.getPageSize(), pageEntity.getPageNum() * pageEntity.getPageSize());
    }


}
