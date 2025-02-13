package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Handler {

    protected StringBuilder query;

    protected List<String> queryParam;

    public Handler() {
        queryParam = new ArrayList<>();
        this.query = new StringBuilder("SELECT routes.id,routes.departure_city , routes.arrival_city,routes.departure_time, routes.arrival_time,type_name, routes.price  FROM routes INNER JOIN main.transport_types tt on routes.transport_type_id = tt.id WHERE ");
    }

    protected Handler nextNode;

    public abstract StringBuilder next(ParamsEntity entity);

    public void nextNode(Handler handler) {
        this.nextNode = handler;
        handler.query = query;
        handler.queryParam = queryParam;
    }

    public String bulid() {
        return query.append("LIMIT 5;").toString();
    }

    public List<String> getQueryParam() {
        return this.queryParam;
    }

    protected boolean isPreWhere() {
        return query.lastIndexOf("WHERE ") + 5 == query.length() - 1;
    }

    public void queryClear() {
        this.query = new StringBuilder("SELECT routes.id,routes.departure_city , routes.arrival_city,routes.departure_time, routes.arrival_time,type_name, routes.price  FROM routes INNER JOIN main.transport_types tt on routes.transport_type_id = tt.id WHERE ");
        this.queryParam = new ArrayList<>();
        if (nextNode == null) {
            return;
        }
        nextNode(nextNode);
    }
}
