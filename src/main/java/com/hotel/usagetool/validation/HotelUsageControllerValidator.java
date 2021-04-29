package com.hotel.usagetool.validation;

import com.hotel.usagetool.domain.HotelUsageRequest;
import com.hotel.usagetool.error.ApplicationError;
import com.hotel.usagetool.exception.ApplicationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.hotel.usagetool.error.ApplicationError.NEGATIVE_INTEGERS_ZERO_ERROR;

/**
 * Aspect validator , validating input on Hotel Usage Controller end-point.
 */
@Aspect
@Component
public class HotelUsageControllerValidator implements Validator {


    /**
     * Validates input before calling the underlying service.
     *
     * @param joinPoint the given join point.
     */
    @Before("execution (* com.hotel.usagetool.controller.HotelUsageController.getCalculatedHotelUsage(..))")
    @Override
    public void validate(JoinPoint joinPoint) {
        Object payload = joinPoint.getArgs()[0];
        isPayloadValid(payload);

        HotelUsageRequest request = (HotelUsageRequest) payload;

        isPositiveInteger(request.getFreePremiumRooms());
        isPositiveInteger(request.getFreeEconomyRooms());
        arePricesValid(request.getPotentialGuestPrices());

    }


    private void isPayloadValid(Object payload) {
        if (!(payload instanceof HotelUsageRequest))
            throw new ApplicationException(ApplicationError.INVALID_PAYLOAD_FORM_ERROR);
    }

    private void isPositiveInteger(int number) {
        if (number <= 0)
            throw new ApplicationException(NEGATIVE_INTEGERS_ZERO_ERROR);
    }

    private void arePricesValid(List<BigDecimal> prices) {
        prices.forEach(
                price -> {
                    if (price.doubleValue() <= 0)
                        throw new ApplicationException(NEGATIVE_INTEGERS_ZERO_ERROR);
                }
        );
    }
}
