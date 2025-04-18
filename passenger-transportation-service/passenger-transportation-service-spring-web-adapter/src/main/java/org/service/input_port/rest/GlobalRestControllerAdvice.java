package org.service.input_port.rest;

import org.service.exception.ProblemDetailsException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(exception = {ProblemDetailsException.class})
    public ResponseEntity<?> exHandler(ProblemDetailsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(ex.getCode()), ex.getMessage());
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }
}
