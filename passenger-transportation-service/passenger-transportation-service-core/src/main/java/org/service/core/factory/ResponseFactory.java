package org.service.core.factory;

import org.service.entity.RouteStepEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseFactory {

    private final static EdgeFactory edgeFactory;
    private final static NodeFactory nodeFactory;


    static {
        edgeFactory = new EdgeFactory();
        nodeFactory = new NodeFactory();
    }

    public static Set<Map<String, String>> createResponsePart(List<RouteStepEntity> routeStepEntity, Type type) {
        return switch (type) {
            case EDGE -> edgeFactory.apply(routeStepEntity);
            case NODE -> nodeFactory.apply(routeStepEntity);
        };
    }

}
