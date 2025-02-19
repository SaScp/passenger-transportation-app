package org.service.ouptput_port.filter_handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        root.fetch("transportType");
        TypeParamHandler typeParamHandler = new TypeParamHandler(builder, root);
        FromParamHandler fromParamHandler = new FromParamHandler(builder, root);
        ToParamHandler toParamHandler = new ToParamHandler(builder, root);
        TimeParamHandler timeParamHandler = new TimeParamHandler(builder, root);
        IdParamHandler idParamHandler = new IdParamHandler(builder, root);

        timeParamHandler.nextNode(typeParamHandler);
        typeParamHandler.nextNode(fromParamHandler);
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
