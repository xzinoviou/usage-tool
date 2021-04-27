package com.hotel.usagetool.handler;

import com.hotel.usagetool.error.ErrorResponse;
import com.hotel.usagetool.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * This handler handles all the errors for rest-calls.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(HttpServletRequest request, ApplicationException ex) {
        ErrorResponse errorResponse = buildErrorResponse(request, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(HttpServletRequest request, ApplicationException ex) {
        return ErrorResponse.builder()
                .errorCode(ex.getError().getErrorCode())
                .description(ex.getError().getDescription())
                .path(request.getServletPath())
                .timestamp(Instant.now().toString())
                .build();
    }
}
