package com.hotel.usagetool.domain;

import lombok.Data;

/**
 * This class represents the total hotel usage,
 * for both premium & economy rooms.
 */
@Data
public class HotelUsage {

    private RoomsUsage premium;
    private RoomsUsage economy;
}
