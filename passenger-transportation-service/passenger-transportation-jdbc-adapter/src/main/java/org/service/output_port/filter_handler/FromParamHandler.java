package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Map;
import java.util.Optional;

public class FromParamHandler extends Handler{
    @Override
    public StringBuilder next(ParamsEntity entity) {
        if (Optional.ofNullable(entity.from()).isPresent()) {
            if (isPreWhere()) {
                query.append(" departure_city = ? ");
            } else {
                query.append("AND departure_city = ? ");
            }
            queryParam.add(  entity.from());
        }
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this.query;
        }
        return nextNode.next(entity);
    }
}
