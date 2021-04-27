package com.hotel.usagetool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the total hotel usage,
 * for both premium & economy rooms.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelUsage {

    private RoomsUsage premium;
    private RoomsUsage economy;
}
