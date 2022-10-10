package com.inditex.prices.exception;

public class ValidationException extends RuntimeException {
  public ValidationException(String description) {
    super(description);
  }

}