package org.service.ouptput_port.filter_handler;



import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.service.entity.ParamsEntity;
import org.service.ouptput_port.model.Route;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlerExecutor {

    private final Handler parentHandler;

    public HandlerExecutor(CriteriaBuilder builder, Root<Route> root) {
        this.parentHandler = Handler.createHandler(builder, root);
    }

    public List<Predicate> execute(ParamsEntity entity) {
        return this.parentHandler.next(entity, new ArrayList<>()).build();
    }

    public Root<Route> getRoot() {
        return this.parentHandler.getRoot();
    }
}
