package org.service.output_port.filter_handler;


import org.service.entity.ParamsEntity;

import java.util.Optional;

public class IdParamHandler extends Handler {

    protected IdParamHandler() {

    }

    @Override
    protected void addParam(ParamsEntity entity) {
        if (Optional.ofNullable(entity.getRouteId()).isPresent()) {
            addAnd();
            query.append(" t_routes.id = ? ");
            queryParam.add(entity.getRouteId());
        }
    }
}
