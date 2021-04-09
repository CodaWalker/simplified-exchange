package ru.test.simplifiedexchange.error;

import lombok.Getter;

import java.io.IOException;

public class CommonException extends IOException {

  @Getter
  private final int code;

  public CommonException(String message, int code) {
    super(message);
    this.code = code;
  }

  public CommonException(ErrorInfo errorInfo) {
    this(errorInfo.getMessage(), errorInfo.getCode());
  }
}
