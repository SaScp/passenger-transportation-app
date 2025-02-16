package org.service.output_port.jdbc;

import org.service.entity.BookingEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.Result;
import org.service.entity.RoutesEntity;
import org.service.output_port.FindByParamsTransportationServiceOutputPort;
import org.service.output_port.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.LruIdCache;
import org.service.output_port.factory.BookingFactory;
import org.service.output_port.filter_handler.SQLConstant;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


public class TransportationJdbcFindByPhoneMapper extends MappingSqlQuery<BookingEntity> implements FindByPhoneTransportationServiceOutputPort, TransportationJdbcAdapter {
    private final LruIdCache<String, List<BookingEntity>> lruIdCache;

    public TransportationJdbcFindByPhoneMapper(DataSource ds, LruIdCache<String, List<BookingEntity>> cache) {
        super(ds, SQLConstant.SELECT_ALL_BOOKING_BY_PHONE);
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.lruIdCache = cache;
    }


    @Override
    public List<BookingEntity> findBy(String phone) {
        if (lruIdCache.containsKey(phone)) {
            return lruIdCache.get(phone);
        }
        List<BookingEntity> entities = this.execute(phone);
        lruIdCache.put(phone, entities);
        return entities;
    }

    @Override
    protected BookingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BookingFactory.createBooking(rs);
    }
}
