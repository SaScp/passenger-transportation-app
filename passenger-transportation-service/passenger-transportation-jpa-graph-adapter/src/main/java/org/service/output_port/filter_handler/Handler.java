package org.service.output_port.filter_handler;

import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.service.entity.ParamsEntity;
import org.service.output_port.model.Route;


import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class Handler {

    protected List<Optional<Predicate>> criteriaPredicate;

    protected Root<Route> root;

    protected CriteriaBuilder builder;

    protected CriteriaQuery<String> criteriaQuery;


    protected Handler nextNode;

    protected Handler(CriteriaBuilder builder, Root<Route> root) {
        this.builder = builder;
        this.root = root;
    }

    public static Handler createHandler(CriteriaBuilder builder, Root<Route> root) {

        TypeParamHandler typeParamHandler = new TypeParamHandler(builder, root);
        FromParamHandler fromParamHandler = new FromParamHandler(builder, root);
        ToParamHandler toParamHandler = new ToParamHandler(builder, root);
        TimeParamHandler timeParamHandler = new TimeParamHandler(builder, root);



        typeParamHandler.nextNode(timeParamHandler);
        timeParamHandler.nextNode(fromParamHandler);
        fromParamHandler.nextNode(toParamHandler);
        return typeParamHandler;
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
