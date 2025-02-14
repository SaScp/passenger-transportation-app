package org.service.output_port.jdbc;

import org.service.entity.BookingParamsEntity;
import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.FindAllTransportationServiceOutputPort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.UUID;

@Component
public class TransportationJdbcCreateBookingMapper extends SqlUpdate implements CreateBookingTransportationServiceOutputPort, TransportationJdbcAdapter {

    public TransportationJdbcCreateBookingMapper(DataSource ds) {
        super(ds, "INSERT INTO t_bookings(id, route_id, booking_time, status_id) VALUES (?,?,?,?);");
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.VARCHAR));
        this.declareParameter(new SqlParameter(Types.INTEGER));
    }

    @Override
    public boolean create(BookingParamsEntity entity) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .toFormatter();
        try {
            String format = formatter.format(LocalDateTime.now());
            String id = UUID.randomUUID().toString();
            this.update(id, entity.getRouteId(), format, 1);
            return insertUser(entity.getNumberPhone(), id);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean insertUser(String numberPhone, String bookingId) {
        return Boolean.TRUE.equals(getJdbcTemplate().execute((ConnectionCallback<Boolean>) e -> {

            try (PreparedStatement insertUser = e.prepareStatement("INSERT INTO t_user(user_phone) VALUES (?)");
                 PreparedStatement selectUser = e.prepareStatement("SELECT exists(SELECT user_phone FROM t_user WHERE user_phone = ?)");
                 PreparedStatement insertUserBooking = e.prepareStatement("INSERT INTO t_user_bookings(user_phone, booking_id) VALUES (?,?)")) {

                selectUser.setString(1, numberPhone);
                if (selectUser.executeQuery().getInt(1) == 0) {
                    insertUser.setString(1, numberPhone);
                    insertUser.execute();

                }
                insertUserBooking.setString(1, numberPhone);
                insertUserBooking.setString(2, bookingId);
                 insertUserBooking.execute();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }));

    }


}
