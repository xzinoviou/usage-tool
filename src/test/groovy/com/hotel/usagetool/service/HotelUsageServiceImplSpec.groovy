package com.hotel.usagetool.service

import spock.lang.Specification

class HotelUsageServiceImplSpec extends Specification {

    private HotelUsageService testClass;

    void setup() {
        testClass = new HotelUsageServiceImpl()
    }

    void cleanup() {
        testClass = null
    }


    def "calculate hotel usage with 3 premium rooms & 3 economy"() {

        given:
        def numbers = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

        when:
        def result = testClass.calculateHotelUsage(3, 3, numbers)

        then:
        with(result.premium) {
            rooms == 3
            reservedRooms == 3
            totalUsage == 738
        }

        with(result.economy) {
            rooms == 3
            reservedRooms == 3
            totalUsage == 167.99
        }
    }

    def "calculate hotel usage with 7 premium rooms & 5 economy"() {

        given:
        def numbers = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

        when:
        def result = testClass.calculateHotelUsage(7, 5, numbers)

        then:
        with(result.premium) {
            rooms == 7
            reservedRooms == 7
            totalUsage == 1153.99
        }

        with(result.economy) {
            rooms == 5
            reservedRooms == 3
            totalUsage == 90
        }
    }

    def "calculate hotel usage with 2 premium rooms & 7 economy"() {

        given:
        def numbers = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

        when:
        def result = testClass.calculateHotelUsage(2,7, numbers)

        then:
        with(result.premium) {
            reservedRooms == 2
            totalUsage == 583
        }

        with(result.economy) {
            reservedRooms == 4
            totalUsage == 189.99
        }
    }

    def "calculate hotel usage with 7 premium rooms & 1 economy"() {

        given:
        def numbers = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

        when:
        def result = testClass.calculateHotelUsage(7, 1, numbers)

        then:
        with(result.premium) {
            reservedRooms == 7
            totalUsage == 1153.99
        }

        with(result.economy) {
            reservedRooms == 1
            totalUsage == 45
        }
    }


}
