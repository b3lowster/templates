package com.example.service.tool;


import com.example.model.exception.BadRequestException;
import com.example.model.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorDescription> httpAbstractExceptionHandler(UserNotFoundException e,
                                                                            HttpServletRequest request)
    {
        log.error(String.format("Handle UserNotFoundException in %s:", request.getServletPath()), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("404");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorDescription> httpAbstractExceptionHandler(BadRequestException e,
                                                                            HttpServletRequest request)
    {
        log.error(String.format("Handle BadRequestException in %s:", request.getServletPath()), e);
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setCode("400");
        errorDescription.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorDescription {
        private String code;
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ValidationErrorDescription {
        private String code;
        private String message;
        private Map<String, DetailedInfo> errors = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DetailedInfo {
        private TranslatedMessage message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TranslatedMessage {
        private String ru;
        private String en;
    }
}
