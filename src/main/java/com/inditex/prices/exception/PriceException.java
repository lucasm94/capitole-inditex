package com.inditex.prices.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PriceException extends RuntimeException {
  private HttpStatus status;

  public PriceException(String description, HttpStatus status) {
    super(description);
    this.status = status;
  }
}
