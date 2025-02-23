package org.service.core;

import org.service.exception.ProblemDetailsException;

public class IsNotPhoneException extends ProblemDetailsException {
    public IsNotPhoneException() {
        super(400, "Вы ввели не номер телефона!");
    }
}
