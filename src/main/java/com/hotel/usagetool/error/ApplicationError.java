package com.hotel.usagetool.error;

/**
 * The error to be returned if a call is not successful.
 */
public enum ApplicationError implements Error {

  INVALID_DATA_ERROR(1001, "Invalid data input"),
  NEGATIVE_INTEGERS_ZERO_ERROR(1002, "Negative Integers or Zeros are not allowed"),
  SYSTEM_ERROR(1003, "System error"),
  VALIDATION_ERROR(1004, "Validation error");

  private final int code;

  private final String description;

  ApplicationError(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
