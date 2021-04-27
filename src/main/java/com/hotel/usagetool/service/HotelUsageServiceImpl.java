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
    public HotelUsage calculateHotelUsage(int freePremiumRooms, int freeEconomyRooms, List<Number> potentialGuestPrices) {
        List<PotentialGuest> potentialGuests = convertPricesToPotentialGuests(potentialGuestPrices);

        RoomsUsage premium = RoomsUsage.builder().rooms(freePremiumRooms).build();
        RoomsUsage economy = RoomsUsage.builder().rooms(freeEconomyRooms).build();

        return calculateUsage(premium, economy, potentialGuests);
    }

    private HotelUsage calculateUsage(RoomsUsage premium, RoomsUsage economy, List<PotentialGuest> potentialGuests) {
        for (PotentialGuest guest : potentialGuests) {

            if (allRoomsReserved(premium, economy))
                break;

            if (isReservationPossible(guest, PotentialGuest.Type.PREMIUM, premium)) {
                updateReservationData(premium, guest, PotentialGuest.Type.PREMIUM);
            }

            if (isReservationPossible(guest, PotentialGuest.Type.ECONOMY, premium)) {
                updateReservationData(premium, guest, PotentialGuest.Type.UPGRADED);
            }

            if (isReservationPossible(guest, PotentialGuest.Type.ECONOMY, economy)) {
                updateReservationData(economy, guest, PotentialGuest.Type.ECONOMY);
            }
        }

        return HotelUsage.builder().premium(premium).economy(economy).build();
    }

    private List<PotentialGuest> convertPricesToPotentialGuests(List<Number> potentialGuestPrices) {
        return potentialGuestPrices.stream()
                .mapToDouble(Number::doubleValue)
                .boxed()
                .sorted((price1, price2) -> Double.compare(price2, price1))
                .map(this::buildGuest)
                .collect(Collectors.toList());
    }

    private PotentialGuest buildGuest(Double price) {
        return PotentialGuest.builder()
                .price(price)
                .type(selectCustomerType(price))
                .availableForReservation(true)
                .build();
    }

    private PotentialGuest.Type selectCustomerType(Double price) {
        return price >= PREMIUM_PRICE_LIMIT ? PREMIUM : ECONOMY;
    }

    private boolean isGuestOfType(PotentialGuest guest, PotentialGuest.Type guestType) {
        return guest.getType().equals(guestType);
    }

    private boolean isAnyRoomAvailable(RoomsUsage roomsUsage) {
        return roomsUsage.getReservedRooms() < roomsUsage.getRooms();
    }

    private boolean isReservationPossible(PotentialGuest guest, PotentialGuest.Type guestType, RoomsUsage roomsUsage) {
        return isGuestOfType(guest, guestType) && isAnyRoomAvailable(roomsUsage) && guest.isAvailableForReservation();
    }

    private boolean allRoomsReserved(RoomsUsage premium, RoomsUsage economy) {
        return premium.getReservedRooms() == premium.getRooms() &&
                economy.getReservedRooms() == economy.getRooms();
    }

    private void updateReservationData(RoomsUsage roomsUsage, PotentialGuest potentialGuest, PotentialGuest.Type guestType) {
        updateRoomsUsageData(roomsUsage, potentialGuest);
        updatePotentialGuestData(potentialGuest, guestType);
    }

    private void updateRoomsUsageData(RoomsUsage roomsUsage, PotentialGuest potentialGuest) {
        roomsUsage.increaseUsageBy(potentialGuest.getPrice());
        roomsUsage.incrementReservedRoomsBy(1);
    }

    private void updatePotentialGuestData(PotentialGuest potentialGuest, PotentialGuest.Type guestType) {
        potentialGuest.setType(guestType);
        potentialGuest.setAvailableForReservation(false);
    }
}
