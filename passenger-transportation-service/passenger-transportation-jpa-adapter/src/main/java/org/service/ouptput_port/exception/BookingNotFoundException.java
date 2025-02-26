package org.service.ouptput_port.exception;

import org.service.exception.ProblemDetailsException;

public class BookingNotFoundException extends ProblemDetailsException {

    public BookingNotFoundException( ) {
        super(404, "Booking Not found");
    }

    public BookingNotFoundException( String message) {
        super(404, message);
    }
}
