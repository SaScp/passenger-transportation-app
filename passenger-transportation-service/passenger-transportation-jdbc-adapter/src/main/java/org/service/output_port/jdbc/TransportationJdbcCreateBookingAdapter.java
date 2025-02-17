package org.service.output_port.jdbc;

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.LruIdCache;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.UUID;


public class TransportationJdbcCreateBookingAdapter extends SqlUpdate implements CreateBookingTransportationServiceOutputPort, TransportationJdbcAdapter {
    private final LruIdCache<String, List<BookingEntity>> lruIdCache;


    public TransportationJdbcCreateBookingAdapter(DataSource ds, LruIdCache<String, List<BookingEntity>> lruIdCache) throws SQLException {
        super(ds, "INSERT INTO t_bookings(id, route_id, booking_time, status_id) VALUES (?,?,?,?);");
        this.lruIdCache = lruIdCache;
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.INTEGER));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BookingParamsEntity entity) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .toFormatter();
        try {
            String format = formatter.format(LocalDateTime.now());
            String id = UUID.randomUUID().toString();
            this.update(id, entity.getRouteId(), format, 1);
            insertUser(entity.getNumberPhone(), id);
            lruIdCache.remove(entity.getNumberPhone());
        } catch (ProblemDetailsException e) {
            throw new ProblemDetailsException(e);
        }
    }

    private void insertUser(String numberPhone, String bookingId) {
        getJdbcTemplate().execute((ConnectionCallback<?>) e -> {
                    InsertUserBookingUtils utils = new InsertUserBookingUtils(e);
                    try {
                        utils.insertUser(numberPhone);
                        utils.insertUserBooking(numberPhone, bookingId);
                    } catch (Exception ex) {
                        throw new ProblemDetailsException(500, ex.getMessage());
                    }
                    return null;
                }
        );
    }


}
