package com.inditex.prices.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.inditex.prices.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PriceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {
    var errorResponse = ErrorResponse.builder()
        .error(BAD_REQUEST.name())
        .message(ex.getMessage())
        .status(BAD_REQUEST.value())
        .build();
    return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), BAD_REQUEST, request);
  }

  @ExceptionHandler(PriceException.class)
  public ResponseEntity<?> handlePriceException(PriceException ex, WebRequest request) {
    var errorResponse = ErrorResponse.builder()
        .error(ex.getStatus().name())
        .message(ex.getMessage())
        .status(ex.getStatus().value())
        .build();
    return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status,
                                                           WebRequest request) {
    var errorResponse = ErrorResponse.builder()
        .error(status.name())
        .message(ex.getMessage())
        .status(status.value())
        .build();
    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
  }

}
