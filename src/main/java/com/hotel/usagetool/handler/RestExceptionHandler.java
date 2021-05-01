package com.hotel.usagetool.handler;

import static com.hotel.usagetool.error.ApplicationError.INVALID_DATA_ERROR;

import com.hotel.usagetool.error.Error;
import com.hotel.usagetool.error.ErrorResponse;
import com.hotel.usagetool.error.Violation;
import com.hotel.usagetool.exception.ApplicationException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This handler handles all the errors for rest-calls.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  protected ResponseEntity<Object> handleApplicationException(HttpServletRequest request,
                                                              ApplicationException ex) {
    var errorResponse = buildErrorResponse(request.getServletPath(), ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private ErrorResponse buildErrorResponse(String path, ApplicationException ex) {
    return ErrorResponse.builder()
        .code(ex.getError().getCode())
        .description(ex.getError().getDescription())
        .path(path)
        .timestamp(Instant.now().toString())
        .build();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

    ErrorResponse error =
        buildValidationError(request.getContextPath(), ex, status, INVALID_DATA_ERROR);

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private ErrorResponse buildValidationError(String path, MethodArgumentNotValidException ex,
                                             HttpStatus status, Error error) {
    return ErrorResponse.builder()
        .path(path)
        .violations(createListOfViolations(ex.getBindingResult()))
        .timestamp(Instant.now().toString())
        .description(error.getDescription())
        .code(status.value())
        .build();
  }

  private List<Violation> createListOfViolations(BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      return Collections.emptyList();
    }

    return bindingResult.getAllErrors()
        .stream()
        .map(this::buildViolation)
        .collect(Collectors.toList());
  }

  private Violation buildViolation(ObjectError objectError) {
    FieldError fieldError = (FieldError) objectError;
    return Violation.builder()
        .fieldName(fieldError.getField())
        .message(objectError.getDefaultMessage())
        .supplied(fieldError.getRejectedValue().toString())
        .build();
  }
}
