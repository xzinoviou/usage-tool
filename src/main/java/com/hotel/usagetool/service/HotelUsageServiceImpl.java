package com.hotel.usagetool.service;

import com.hotel.usagetool.domain.HotelUsage;
import com.hotel.usagetool.domain.PotentialGuest;
import com.hotel.usagetool.domain.RoomsUsage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.hotel.usagetool.constants.Constant.PREMIUM_PRICE_LIMIT;
import static com.hotel.usagetool.domain.PotentialGuest.Type.ECONOMY;
import static com.hotel.usagetool.domain.PotentialGuest.Type.PREMIUM;

/**
 * Implementation of {@link HotelUsageService}.
 */
@Service
public class HotelUsageServiceImpl implements HotelUsageService {

    @Override
    public HotelUsage calculateHotelUsage(int freePremiumRooms, int freeEconomyRooms,
                                          List<Number> potentialGuestPrices) {
        List<PotentialGuest> potentialGuests = convertPricesToPotentialGuests(potentialGuestPrices);

        RoomsUsage premium = RoomsUsage.builder().rooms(freePremiumRooms).build();
        RoomsUsage economy = RoomsUsage.builder().rooms(freeEconomyRooms).build();

        return calculateHotelTotalUsage(premium, economy, potentialGuests);
    }

    /**
     * Calculate the hotel total usage in both premium  economy rooms , by a list of potentialGuests.
     *
     * @param premium the given premium usage.
     * @param economy the given economy usage.
     * @param potentialGuests the given potential guests list.
     * @return the calculated hotel total usage.
     */
    private HotelUsage calculateHotelTotalUsage(RoomsUsage premium, RoomsUsage economy,
                                                List<PotentialGuest> potentialGuests) {
        for (PotentialGuest guest : potentialGuests) {

            if (allRoomsReserved(premium, economy))
                break;

            if (isReservationPossible(guest, PotentialGuest.Type.PREMIUM, premium)) {
                reserve(premium, guest, PotentialGuest.Type.PREMIUM);
            }

            if (isReservationPossible(guest, PotentialGuest.Type.ECONOMY, premium)) {
                reserve(premium, guest, PotentialGuest.Type.UPGRADED);
            }

            if (isReservationPossible(guest, PotentialGuest.Type.ECONOMY, economy)) {
                reserve(economy, guest, PotentialGuest.Type.ECONOMY);
            }
        }

        return HotelUsage.builder().premium(premium).economy(economy).build();
    }

    /**
     * Convert the list of prices to a list of Potential guests , for ease of manipulation <br>
     * in reservations and hotel usage calculation.
     * @param potentialGuestPrices the given list of potential guests prices.
     * @return the list of potential guests.
     */
    private List<PotentialGuest> convertPricesToPotentialGuests(List<Number> potentialGuestPrices) {
        return potentialGuestPrices.stream()
                .mapToDouble(Number::doubleValue)
                .boxed()
                .sorted((price1, price2) -> Double.compare(price2, price1))
                .map(this::buildPotentialGuest)
                .collect(Collectors.toList());
    }

    /**
     * Hide a Potential Guest builder inside a method , and create the new instance by a given price.
     *
     * @param price the given price.
     * @return the new instance of PotentialGuest.
     */
    private PotentialGuest buildPotentialGuest(Double price) {
        return PotentialGuest.builder()
                .price(price)
                .type(selectCustomerType(price))
                .availableForReservation(true)
                .build();
    }

    /**
     * Returns a Potential Guest type based on a given price rule.
     *
     * @param price the given price.
     * @return the PotentialGuest.Type .
     */
    private PotentialGuest.Type selectCustomerType(Double price) {
        return price >= PREMIUM_PRICE_LIMIT ? PREMIUM : ECONOMY;
    }

    /**
     * Returns a boolean result of the PotentialGuest type based <br>
     * on the given potential guest <br>
     * and a given type matching.
     *
     * @param potentialGuest the given guest.
     * @param guestType      the given guest type.
     * @return the boolean result.
     */
    private boolean isPotentialGuestOfType(PotentialGuest potentialGuest, PotentialGuest.Type guestType) {
        return potentialGuest.getType().equals(guestType);
    }

    /**
     * Returns a boolean result based on comparison of reserved rooms to rooms <br>
     * of a given rooms usage.
     *
     * @param roomsUsage the given rooms usage.
     * @return the boolean result.
     */
    private boolean isAnyRoomAvailable(RoomsUsage roomsUsage) {
        return roomsUsage.getReservedRooms() < roomsUsage.getRooms();
    }


    /**
     * Returns a boolean result if a reservation is possible , on the comparison of <br>
     * potential guest type , room availability and guest availability for reservation.
     *
     * @param potentialGuest the given potential guest.
     * @param guestType      the given guest type.
     * @param roomsUsage     the given room usage.
     * @return the boolean result.
     */
    private boolean isReservationPossible(PotentialGuest potentialGuest,
                                          PotentialGuest.Type guestType, RoomsUsage roomsUsage) {
        return isPotentialGuestOfType(potentialGuest, guestType) && isAnyRoomAvailable(roomsUsage)
                && potentialGuest.isAvailableForReservation();
    }

    /**
     * REturns a boolean result if all premium & economy rooms are reserved, <br>
     * based on a given premium room usage and an economy room usage.
     *
     * @param premium the given premium usage.
     * @param economy the given economy usage.
     * @return the bollean result.
     */
    private boolean allRoomsReserved(RoomsUsage premium, RoomsUsage economy) {
        return premium.getReservedRooms() == premium.getRooms() &&
                economy.getReservedRooms() == economy.getRooms();
    }

    /**
     * Reserve a room , and delegate the reservation to both room & potential guest <br>
     * that are bound for the reservation.
     *
     * @param roomsUsage     the room usage.
     * @param potentialGuest the potential guest.
     * @param guestType      the guest type of the reservation.
     */
    private void reserve(RoomsUsage roomsUsage, PotentialGuest potentialGuest, PotentialGuest.Type guestType) {
        roomUsageReservation(roomsUsage, potentialGuest.getPrice());
        potentialGuestReservation(potentialGuest, guestType);
    }

    /**
     * Mimics the reservation of a room and the changes in the room usage <br>
     * which should take place, such as the price and the increment of the reserved rooms.
     *
     * @param roomsUsage the given rooms usage.
     * @param price      the given price for the reservation.
     */
    private void roomUsageReservation(RoomsUsage roomsUsage, Double price) {
        roomsUsage.increaseUsageBy(price);
        roomsUsage.incrementReservedRoomsBy(1);
    }

    /**
     * Mimics the reservation of a potential guest and the changes in his data <br
     * which should take place, such as guest type and the availableForReservation flag.
     *
     * @param potentialGuest the potential guest.
     * @param guestType      the guest type with which the guest will make a reservation.
     */
    private void potentialGuestReservation(PotentialGuest potentialGuest, PotentialGuest.Type guestType) {
        potentialGuest.setType(guestType);
        potentialGuest.setAvailableForReservation(false);
    }
}
