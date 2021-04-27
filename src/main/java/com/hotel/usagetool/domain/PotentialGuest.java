package com.hotel.usagetool.domain;

import lombok.Data;

/**
 * Represents the potential guest <br>
 * for use in estimating the hotel usage.
 */
@Data
public class PotentialGuest {

    private double price;
    private Type type;
    private boolean availableForReservation;

    public enum Type {
        PREMIUM, ECONOMY, UPGRADE
    }
}
