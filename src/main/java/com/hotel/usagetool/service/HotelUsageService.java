package com.hotel.usagetool.service;

import com.hotel.usagetool.domain.HotelUsage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Hotel Usage Service for estimating the hotel usage.
 */
public interface HotelUsageService {

    /**
     * Calculates the hotel usage by given the numbers of :<br>
     * -freePremiumRooms
     * -freeEconomyRooms
     * -a list of potential guest prices<br>
     *
     * @param freePremiumRooms     an integer representing the free premium rooms.
     * @param freeEconomyRooms     an integer representing the free economy rooms.
     * @param potentialGuestPrices a list of numbers representing the potential guest prices.<br>
     *                             All types extending the Number class are acceptable.
     * @return the estimation of the hotel usage.
     */
    HotelUsage calculateHotelUsage(int freePremiumRooms, int freeEconomyRooms, List<BigDecimal> potentialGuestPrices);
}
