package com.hotel.usagetool.service;

import com.hotel.usagetool.domain.HotelUsage;

/**
 * Hotel Usage Service for estimating the hotel usage.
 */
public interface HotelUsageService {

    /**
     * Calculates the hotel usage by given the numbers of :<br>
     * -freePremiumRooms
     * -freeEconomyRooms
     * -an array of potential guest prices<br>
     *
     * @param freePremiumRooms     an integer representing the free premium rooms.
     * @param freeEconomyRooms     an integer representing the free economy rooms.
     * @param potentialGuestPrices an array of numbers representing the potential guest prices.<br>
     *                             All types extending the Number class are acceptable.
     * @return the estimation of the hotel usage.
     */
    HotelUsage calculateHotelUsage(int freePremiumRooms, int freeEconomyRooms, Number[] potentialGuestPrices);
}
