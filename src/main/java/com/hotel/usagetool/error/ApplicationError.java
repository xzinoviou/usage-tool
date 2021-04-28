package com.hotel.usagetool.error;

/**
 * The error to be returned if a call is not successful.
 */
public enum ApplicationError implements Error {

    INVALID_PAYLOAD_FORM_ERROR("1001", "Invalid payload form input"),
    NEGATIVE_INTEGERS_ZERO_ERROR("1002", "Negative Integers or Zeros are not allowed"),
    SYSTEM_ERROR("1003", "System error");

    private final String errorCode;

    private final String description;

    ApplicationError(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
