package org.service.passangertransportationgraphjpaadapter.filter_handler;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.service.passangertransportationgraphjpaadapter.dto.ParamsEntity;
import org.service.passangertransportationgraphjpaadapter.model.Edge;
import org.service.passangertransportationgraphjpaadapter.model.Location;
import org.service.passangertransportationgraphjpaadapter.model.Route;
import org.service.passangertransportationgraphjpaadapter.model.RouteStep;


import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class Handler {

    protected List<Optional<Predicate>> criteriaPredicate;

    protected Root<Route> root;

    protected CriteriaBuilder builder;

    protected Handler nextNode;

    protected Handler(CriteriaBuilder builder, Root<Route> root) {
        this.builder = builder;
        this.root = root;
    }

    public static TimeParamHandler createHandler(CriteriaBuilder builder, Root<Route> root) {

        root.fetch("departureCity");
        root.fetch("arrivalCity");

        Fetch<Route, RouteStep> routeStepsFetch = root.fetch("routeSteps");
        Fetch<RouteStep, Location> edgeId = routeStepsFetch.fetch("edgeId");

        edgeId.fetch("fromLocationId");
        edgeId.fetch("toLocationId");

        FromParamHandler fromParamHandler = new FromParamHandler(builder, root);
        ToParamHandler toParamHandler = new ToParamHandler(builder, root);
        TimeParamHandler timeParamHandler = new TimeParamHandler(builder, root);
        IdParamHandler idParamHandler = new IdParamHandler(builder, root);

        timeParamHandler.nextNode(fromParamHandler);
        fromParamHandler.nextNode(toParamHandler);
        toParamHandler.nextNode(idParamHandler);
        return timeParamHandler;
    }


    public Handler next(ParamsEntity entity, List<Optional<Predicate>> criteriaPredicate) {
        this.criteriaPredicate = criteriaPredicate;
        addParam(entity);
        if (Optional.ofNullable(nextNode).isEmpty()) {
            return this;
        }
        return nextNode.next(entity, criteriaPredicate);
    }


    public List<Predicate> build() {
        return criteriaPredicate.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
    public void nextNode(Handler handler) {
        this.nextNode = handler;
        handler.criteriaPredicate = criteriaPredicate;
    }

    protected abstract void addParam(ParamsEntity entity);

}
