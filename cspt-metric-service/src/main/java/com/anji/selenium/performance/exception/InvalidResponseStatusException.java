package com.anji.selenium.performance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidResponseStatusException extends RuntimeException {
  public InvalidResponseStatusException(String message) {
    super(message);
  }
}
