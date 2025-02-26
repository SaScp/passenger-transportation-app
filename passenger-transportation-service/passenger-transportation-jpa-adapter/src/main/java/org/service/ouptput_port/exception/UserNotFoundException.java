package org.service.ouptput_port.exception;

import org.service.exception.ProblemDetailsException;

public class UserNotFoundException extends ProblemDetailsException {
    public UserNotFoundException(String message) {
        super(404, message);
    }
    public UserNotFoundException() {
        super(404, "User not found");
    }
}
