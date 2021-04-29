package com.hotel.usagetool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The RoomsUsage represents the total usage <br>
 * for a number of rooms.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomsUsage {

    private int rooms;
    private int reservedRooms;
    private BigDecimal totalUsage;

    public void increaseUsageBy(BigDecimal amount) {
        this.totalUsage = this.totalUsage.add(amount);
    }

    public void incrementReservedRoomsBy(int newReservations) {
        this.reservedRooms += newReservations;
    }
}
