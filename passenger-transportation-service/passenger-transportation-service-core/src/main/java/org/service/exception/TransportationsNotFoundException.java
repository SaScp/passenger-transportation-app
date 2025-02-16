package org.service.exception;

public class TransportationsNotFoundException extends ProblemDetailsException {

    public TransportationsNotFoundException(int conde, String message) {
        super(conde, message);
    }
}
