package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class TimeParamHandler extends Handler{
    @Override
    public StringBuilder next(ParamsEntity entity) {
        if (Optional.ofNullable(entity.time()).isPresent()) {
            if (isPreWhere()) {
                query.append(" departure_time >= ? ");
            } else {
                query.append("AND departure_time >= ? ");
            }
            queryParam.add( entity.time().toString());
        }
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this.query;
        }
        return nextNode.next(entity);
    }
}
