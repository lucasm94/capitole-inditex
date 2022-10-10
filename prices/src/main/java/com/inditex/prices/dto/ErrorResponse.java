package com.inditex.prices.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponse {
  private String error;
  private String message;
  private Integer status;
}
