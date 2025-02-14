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
            if (isPreWhere()) {
                query.append(" departure_time >= :time ");
            } else {
                query.append("AND departure_time >= :time ");
            }
            queryParam.add(entity.time().toString());
        }
    }
}
