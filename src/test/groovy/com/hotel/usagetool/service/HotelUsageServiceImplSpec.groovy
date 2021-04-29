package com.hotel.usagetool.service

import com.hotel.usagetool.domain.HotelUsageRequest
import spock.lang.Specification
import spock.lang.Unroll

class HotelUsageServiceImplSpec extends Specification {

    private HotelUsageService testClass
    private static List<BigDecimal> prices = [23.0g, 45.0g, 155.0g, 374.0g, 22.0g, 99.99g, 100.0g, 101.0g, 115.0g, 209.0g]

    void setup() {
        testClass = new HotelUsageServiceImpl()
    }

    void cleanup() {
        testClass = null
    }

    @Unroll
    @SuppressWarnings("IncompatibleTypes")
    def "calculate hotel usage with arbitrate premium & economy rooms & prices"() {
        given: "a payload of arbitrate premium & economy rooms and a fixed list of prices"
        def payload = new HotelUsageRequest(
                freePremiumRooms: freePremiumRooms,
                freeEconomyRooms: freeEconomyRooms,
                potentialGuestPrices: prices)

        expect: "reservations to be made according to the rules"
        def result = testClass.calculateHotelUsage(payload.freePremiumRooms, payload.freeEconomyRooms,
                prices)
        result.premium.reservedRooms == reservedPremiumRooms
        result.premium.totalUsage == premiumUsage
        result.economy.reservedRooms == reservedEconomyRooms
        result.economy.totalUsage == economyUsage

        where: "results are verified"
        freePremiumRooms | reservedPremiumRooms | premiumUsage | freeEconomyRooms | reservedEconomyRooms | economyUsage
        3                | 3                    | 738          | 3                | 3                    | 167.99
        7                | 7                    | 1153.99      | 5                | 3                    | 90
        2                | 2                    | 583          | 7                | 4                    | 189.99
        7                | 7                    | 1153.99      | 1                | 1                    | 45
    }


}
