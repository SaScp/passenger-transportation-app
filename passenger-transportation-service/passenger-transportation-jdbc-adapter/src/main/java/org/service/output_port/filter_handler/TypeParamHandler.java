package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.Optional;

public class TypeParamHandler extends Handler {
    @Override
    public StringBuilder next(ParamsEntity entity) {
        if (Optional.ofNullable(entity.type()).isPresent()) {
            if (isPreWhere()) {
                query.append(" type_name = ? ");
            } else {
                query.append("AND type_name = ? ");
            }
            queryParam.add(  entity.type());
        }
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this.query;
        }
        return nextNode.next(entity);
    }
}
