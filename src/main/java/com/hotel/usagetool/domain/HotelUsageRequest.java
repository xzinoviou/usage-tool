package com.hotel.usagetool.domain;

import lombok.Getter;

/**
 * Represents the request sent from the hotel,<br>
 * for the usage calculation , by given free rooms <br>
 * per category(premium, economy) and a given array <br>
 * of prices potential customers are willing to pay.
 */
@Getter
public class HotelUsageRequest {

    private int freePremiumRooms;
    private int freeEconomyRooms;
    private Number[] potentialGuestPrices;

}
