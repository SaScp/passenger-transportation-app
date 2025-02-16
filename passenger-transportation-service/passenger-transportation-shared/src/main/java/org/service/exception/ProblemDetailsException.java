package org.service.exception;

public class ProblemDetailsException extends RuntimeException {

    public int code;

    public ProblemDetailsException(int conde, String message) {
        super(message);
        this.code = conde;
    }

    public ProblemDetailsException(ProblemDetailsException cause) {
        this(cause.code, cause.getMessage());
    }
}
