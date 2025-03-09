package org.service.output_purt.exception;

import org.service.exception.ProblemDetailsException;

public class RouteNotFoundException extends ProblemDetailsException {
    public RouteNotFoundException() {
        super(404, "Route not found");
    }
}
