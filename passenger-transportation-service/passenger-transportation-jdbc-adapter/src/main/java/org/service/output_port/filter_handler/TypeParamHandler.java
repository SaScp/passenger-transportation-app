package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class TypeParamHandler extends Handler {

    protected TypeParamHandler() {

    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.type()).isPresent()) {
            addAnd();
            query.append(" type_name = :type ");
            queryParam.add(entity.type());
        }

    }
}
