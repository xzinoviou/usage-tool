package com.hotel.usagetool.domain;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;

/**
 * Represents the request sent from the hotel,<br>
 * for the usage calculation , by given free rooms <br>
 * per category(premium, economy) and a given array <br>
 * of prices potential customers are willing to pay.
 */
@Getter
public class HotelUsageRequest {

  @Min(value = 0, message = "Min room numbers is 0")
  @Max(value = 5000, message = "Max room numbers is 5000")
  private int freePremiumRooms;

  @Min(value = 0, message = "Min room numbers is 0")
  @Max(value = 5000, message = "Max room numbers is 5000")
  private int freeEconomyRooms;

  private List<
      @Min(value = 1, message = "Min reservation price is 20")
      @Max(value = 10000, message = "Max reservation price is 10.000")
          BigDecimal> potentialGuestPrices;

}
