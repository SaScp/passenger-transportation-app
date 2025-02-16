package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Optional;

public class TimeParamHandler extends Handler{

    protected TimeParamHandler() {
    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.getTime()).isPresent()) {
            addAnd();
            query.append(" departure_time >= ? ");
            queryParam.add(entity.getTime().toString());
        }
    }
}
