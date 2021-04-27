package com.hotel.usagetool.validation;

import org.aspectj.lang.JoinPoint;

/**
 * This Validator interface can be implemented for different validation rules.
 */
public interface Validator {

    /**
     * Validates a given join point on specific rules.
     *
     * @param joinPoint the given join point.
     */
    void validate(JoinPoint joinPoint);
}
