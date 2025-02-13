package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Optional;

public class ToParamHandler extends Handler {
    @Override
    public StringBuilder next(ParamsEntity entity) {
        if (Optional.ofNullable(entity.to()).isPresent()) {
            if (isPreWhere()) {
                query.append(" arrival_city = ? ");
            } else {
                query.append("AND arrival_city = ? ");
            }
            queryParam.add(  entity.to());
        }
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this.query;
        }
        return nextNode.next(entity);
    }
}
