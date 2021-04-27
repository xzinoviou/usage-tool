package com.hotel.usagetool.exception;

import com.hotel.usagetool.error.Error;
import lombok.Getter;

/**
 * This exception is to be thrown for all the unchecked exceptions.
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final Error error;

    public ApplicationException(Error error) {
        this.error = error;
    }
}
