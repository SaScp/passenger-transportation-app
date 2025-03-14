package org.service.output_port.jdbc.adapter;

import org.service.entity.BookingEntity;
import org.service.entity.PageEntity;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.JdbcLruIdCache;
import org.service.output_port.factory.BookingFactory;
import org.service.output_port.filter_handler.SQLConstant;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


public class TransportationJdbcFindByPhoneAdapter extends MappingSqlQuery<BookingEntity> implements FindByPhoneTransportationServiceOutputPort, TransportationJdbcAdapter {

    private final JdbcLruIdCache<String, List<BookingEntity>> lruIdCache;

    public TransportationJdbcFindByPhoneAdapter(DataSource ds, JdbcLruIdCache<String, List<BookingEntity>> cache) {
        super(ds, SQLConstant.SELECT_ALL_BOOKING_BY_PHONE);
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.lruIdCache = cache;
    }


    @Override
    public List<BookingEntity> findBy(String phone, PageEntity pageEntity) {
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
