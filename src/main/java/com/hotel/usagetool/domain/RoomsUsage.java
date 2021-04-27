package com.hotel.usagetool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The RoomsUsage represents the total usage <br>
 * for a number of rooms.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomsUsage {

    private int reservedRooms;
    private double totalUsage;
}
