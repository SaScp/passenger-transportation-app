package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class ToParamHandler extends Handler {

    protected ToParamHandler() {

    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.to()).isPresent()) {
            addAnd();
            query.append(" arrival_city = :to ");
            queryParam.add(entity.to());
        }
    }
}
