package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class FromParamHandler extends Handler{
    protected FromParamHandler() {

    }
    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.from()).isPresent()) {
            addAnd();
            query.append(" departure_city = ? ");
            queryParam.add(entity.from());
        }
    }
}
