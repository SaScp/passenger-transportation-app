package org.service.exception;

public class ProblemDetailsException extends RuntimeException {

    private int code;

    public ProblemDetailsException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ProblemDetailsException(ProblemDetailsException cause) {
        this(cause.code, cause.getMessage());
    }

    public int getCode() {
        return code;
    }
}
