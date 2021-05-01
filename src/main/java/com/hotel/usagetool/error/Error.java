package com.hotel.usagetool.error;

/**
 * This interface is to be implemented from error responses.<br>
 * These errors will be returned when exceptions are thrown.
 */
public interface Error {

    int getCode();

    String getDescription();
}
