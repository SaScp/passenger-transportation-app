package org.service.output_port.jdbc.adapter;

import org.service.entity.BookingEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.LruIdCache;
import org.service.output_port.RevokeBookingTransportationServiceOutputPort;
import org.service.output_port.filter_handler.SQLConstant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;


public class TransportationJdbcRevokeBookingAdapter extends SqlUpdate implements RevokeBookingTransportationServiceOutputPort, TransportationJdbcAdapter {

    private final LruIdCache<String, List<BookingEntity>> lruIdCache;


    public TransportationJdbcRevokeBookingAdapter(DataSource ds, LruIdCache<String, List<BookingEntity>> cache) {
        super(ds, SQLConstant.UPDATE_STATUS);
        this.declareParameter(new SqlParameter(Types.INTEGER));
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.lruIdCache = cache;
    }

    @Override
    @Transactional
    public  void revoke(String id) {
        try {
            this.update(2, id);
            String s = getJdbcTemplate().queryForObject(SQLConstant.SELECT_BOOKING_BY_ID, String.class, id);
            lruIdCache.remove(s);
        }  catch (EmptyResultDataAccessException e) {
            throw new ProblemDetailsException(404, "Бронирование не найдено");
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new ProblemDetailsException(500, "Найдено несколько записей для бронирования");
        } catch (Exception e) {
            throw new ProblemDetailsException(500, "ошибка не удалось отменить бронь");
        }
    }


}
