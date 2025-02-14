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
            if (isPreWhere()) {
                query.append(" departure_city = :from ");
            } else {
                query.append("AND departure_city = :from ");
            }
            queryParam.add(entity.from());
        }
    }
}
