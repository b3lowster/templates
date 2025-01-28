package com.jwtexample.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Token is expired")
public class ExpiredException extends RuntimeException {

    public ExpiredException() {
    }

    public ExpiredException(String message) {
        super(message);
    }
}
