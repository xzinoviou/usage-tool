package com.hotel.usagetool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the potential guest <br>
 * for use in estimating the hotel usage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PotentialGuest {

    private double price;
    private Type type;
    private boolean availableForReservation;

    public enum Type {
        PREMIUM, ECONOMY, UPGRADE
    }
}
