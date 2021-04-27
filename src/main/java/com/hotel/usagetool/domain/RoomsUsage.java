package com.hotel.usagetool.domain;

import lombok.Data;

/**
 * The RoomsUsage represents the total usage <br>
 * for a number of rooms.
 */
@Data
public class RoomsUsage {

    private int reservedRooms;
    private double totalUsage;
}
