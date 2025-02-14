package org.service.output_port.filter_handler;

import org.service.entity.ParamsEntity;
import org.service.entity.Result;

import java.util.*;

public abstract class Handler {

    protected StringBuilder query;

    protected List<String> queryParam;

    protected Handler nextNode;

    protected Handler() {

    }

    public static TimeParamHandler createHandler() {
        TypeParamHandler typeParamHandler = new TypeParamHandler();
        FromParamHandler fromParamHandler = new FromParamHandler();
        ToParamHandler toParamHandler = new ToParamHandler();
        TimeParamHandler timeParamHandler = new TimeParamHandler();

        timeParamHandler.nextNode(typeParamHandler);
        typeParamHandler.nextNode(fromParamHandler);
        fromParamHandler.nextNode(toParamHandler);
        return timeParamHandler;
    }


    public Handler next(ParamsEntity entity, StringBuilder query, List<String> queryParam) {
        this.query = query;
        this.queryParam = queryParam;
        addParam(entity);
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this;
        }
        return nextNode.next(entity,query, queryParam);
    }


    public void nextNode(Handler handler) {
        this.nextNode = handler;
        handler.query = query;
        handler.queryParam = queryParam;
    }

    public Result build() {
        return new Result(queryParam, query.append("LIMIT 5;").toString());
    }


    protected boolean isPreWhere() {
        return query.lastIndexOf("WHERE ") + 5 == query.length() - 1;
    }



    protected abstract void addParam(ParamsEntity entity);

}
