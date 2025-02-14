package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class TimeParamHandler extends Handler{

    protected TimeParamHandler() {
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.time()).isPresent()) {
            addAnd();
            query.append(" departure_time >= :time ");
            queryParam.add(entity.time().toString());
        }
    }
}
