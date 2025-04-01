package org.service.config.schedule;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveScheduleConfiguration {

    private final JdbcTemplate jdbcTemplate;

    private final long TIME_OF_REMOVE_DEPRECATE_BOOKING = 10_800_000;

    public RemoveScheduleConfiguration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedRate = TIME_OF_REMOVE_DEPRECATE_BOOKING)
    public void removeDeprecateBooking() {
        jdbcTemplate.update("DELETE FROM t_booking WHERE status_id = 2");
    }
}
