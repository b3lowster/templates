package com.jwtexample.configuration;

import com.jwtexample.model.exception.BadRequestException;
import com.jwtexample.model.exception.ExpiredException;
import com.jwtexample.model.exception.ForbiddenException;
import com.jwtexample.model.exception.NotFoundException;
import com.jwtexample.model.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorDescription> badRequestExceptionHandler(BadRequestException e,
                                                                         HttpServletRequest request) {
        log.info("Handle BadRequestException in {}:", request.getServletPath(), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("400");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorDescription> forbiddenExceptionHandler(ForbiddenException e,
                                                                         HttpServletRequest request) {
        log.info("Handle ForbiddenException in {}:", request.getServletPath(), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("403");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorDescription> notFoundExceptionHandler(NotFoundException e,
                                                                         HttpServletRequest request) {
        log.info("Handle NotFoundException in {}:", request.getServletPath(), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("404");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorDescription> unauthorizedExceptionHandler(UnauthorizedException e,
                                                                            HttpServletRequest request) {
        log.info("Handle UnauthorizedException in {}:", request.getServletPath(), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("401");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredException.class)
    protected ResponseEntity<ErrorDescription> expiredExceptionHandler(ExpiredException e,
                                                                       HttpServletRequest request) {
        log.info("Handle ExpiredException in {}:", request.getServletPath(), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("410");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.GONE);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorDescription {
        private String code;
        private String message;
    }
}
