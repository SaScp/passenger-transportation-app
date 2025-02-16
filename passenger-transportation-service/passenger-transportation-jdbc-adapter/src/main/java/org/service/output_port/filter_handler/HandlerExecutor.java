package org.service.output_port.filter_handler;



import org.service.entity.ParamsEntity;
import org.service.entity.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandlerExecutor {

    private Handler parentHandler;

    public HandlerExecutor() {
        this.parentHandler = Handler.createHandler();
    }

    public Result execute(ParamsEntity entity) {
        StringBuilder builder = new StringBuilder(SQLConstant.START_SELECT_BY_PARAMS_QUERY);
        List<String> params = new ArrayList<>();

        return this.parentHandler.next(entity, builder, params).build();
    }
}
