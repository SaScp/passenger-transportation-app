package org.service.output_purt.exception;

import org.service.exception.ProblemDetailsException;

public class RouteIsNullException extends ProblemDetailsException {
    public RouteIsNullException() {
        super(400, "Route id is null");
    }
}
