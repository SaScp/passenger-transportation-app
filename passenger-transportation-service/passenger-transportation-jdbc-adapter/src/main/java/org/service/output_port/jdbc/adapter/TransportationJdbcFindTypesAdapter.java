package org.service.output_port.jdbc.adapter;

import org.service.entity.TypeEntity;
import org.service.output_port.find.FindTypesTransportationServiceOutputPort;
import org.service.output_port.JdbcLruIdCache;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransportationJdbcFindTypesAdapter extends MappingSqlQuery<TypeEntity> implements FindTypesTransportationServiceOutputPort {


    public TransportationJdbcFindTypesAdapter(DataSource ds) {
        super(ds, "SELECT type_name FROM t_transport_types");
    }

    @Override
    public List<TypeEntity> findAllTypeEntity() {

        return this.execute();
    }

    @Override
    protected TypeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TypeEntity(rs.getLong("id"), rs.getString("type_name"));
    }
}
