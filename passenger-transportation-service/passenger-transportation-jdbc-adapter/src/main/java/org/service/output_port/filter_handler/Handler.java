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
        IdParamHandler idParamHandler = new IdParamHandler();

        timeParamHandler.nextNode(typeParamHandler);
        typeParamHandler.nextNode(fromParamHandler);
        fromParamHandler.nextNode(toParamHandler);
        fromParamHandler.nextNode(idParamHandler);
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
        return new Result(queryParam, query.append(SQLConstant.END_SELECT_BY_PARAMS_QUERY).toString());
    }

    protected boolean isPreWhere() {
        return query.lastIndexOf(SQLConstant.WHERE) + 5 == query.length() - 1;
    }

    protected void addAnd() {
        if (!isPreWhere()) {
            query.append("AND");
        }
    }

    protected abstract void addParam(ParamsEntity entity);

}
