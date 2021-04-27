package com.hotel.usagetool.validation;

import com.hotel.usagetool.exception.ApplicationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hotel.usagetool.error.ApplicationError.INVALID_DATA_ERROR;
import static com.hotel.usagetool.error.ApplicationError.NEGATIVE_INTEGERS_ZERO_ERROR;

/**
 * Application Validator for generic validation.
 */
@Aspect
@Component
public class HotelUsageValidator implements Validator {

    @Override
    @Before("execution (* com.hotel.usagetool.service.HotelUsageService.calculateHotelUsage(..))")
    public void validate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        isValidFormat(args[0]);
        isValidFormat(args[1]);
        arePricesValid(args[2]);

    }

    private void isValidFormat(Object number) {
        if (!(number instanceof Number))
            throw new ApplicationException(INVALID_DATA_ERROR);

        if (((Number) number).doubleValue() <= 0)
            throw new ApplicationException(NEGATIVE_INTEGERS_ZERO_ERROR);
    }

    private void arePricesValid(Object potentialGuestPrices) {
        if (!(potentialGuestPrices instanceof List))
            throw new ApplicationException(INVALID_DATA_ERROR);

        var prices = (List<Number>) potentialGuestPrices;

        prices.forEach(
                price -> {
                    if (price.doubleValue() <= 0)
                        throw new ApplicationException(NEGATIVE_INTEGERS_ZERO_ERROR);
                }
        );
    }
}
