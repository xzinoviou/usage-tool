package com.hotel.usagetool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the potential guest <br>
 * for use in estimating the hotel usage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PotentialGuest {

    private BigDecimal price;
    private Type type;
    private boolean availableForReservation;

    public enum Type {
        PREMIUM, ECONOMY, UPGRADED
    }
}
