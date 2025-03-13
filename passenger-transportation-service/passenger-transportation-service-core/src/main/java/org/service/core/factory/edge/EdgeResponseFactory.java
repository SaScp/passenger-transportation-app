package org.service.core.factory.edge;

import org.service.core.factory.Type;
import org.service.entity.EdgeEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EdgeResponseFactory {

    private final static EdgeFactory edgeFactory;
    private final static NodeFactory nodeFactory;


    static {
        edgeFactory = new EdgeFactory();
        nodeFactory = new NodeFactory();
    }

    public static Set<Map<String, String>> createResponsePart(List<EdgeEntity> routeStepEntity, Type type) {
        return switch (type) {
            case EDGE -> edgeFactory.apply(routeStepEntity);
            case NODE -> nodeFactory.apply(routeStepEntity);
        };
    }

}
