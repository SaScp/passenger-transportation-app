package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

public class TimeParamHandler extends Handler {

    protected TimeParamHandler() {
    }

    private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MM/dd/yyyy-HH:mm:ss")
            .toFormatter();

    @Override
    protected void addParam(ParamsEntity entity) {
        addAnd();
        query.append(" departure_time >= ? ");
        Optional.ofNullable(entity.getTime())
                .ifPresentOrElse(
                        time -> queryParam.add(entity.getTime().toString()),
                        () -> queryParam.add(LocalDateTime.now().format(formatter))
                );

    }
}
