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
        StringBuilder builder = new StringBuilder("SELECT routes.id,routes.departure_city , routes.arrival_city,routes.departure_time, routes.arrival_time,type_name, routes.price  FROM routes INNER JOIN main.transport_types tt on routes.transport_type_id = tt.id WHERE ");
        List<String> map = new ArrayList<>();

        return this.parentHandler.next(entity, builder, map).build();
    }
}
