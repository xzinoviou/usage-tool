package com.hotel.usagetool.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hotel.usagetool.domain.HotelUsageRequest
import com.hotel.usagetool.service.HotelUsageService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class HotelUsageControllerSpec extends Specification {

    private HotelUsageService hotelUsageService
    private HotelUsageController testClass
    private MockMvc mockMvc
    private ObjectMapper objectMapper
    static def prices = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

    void setup() {
        hotelUsageService = Mock()
        objectMapper = new ObjectMapper()
        testClass = new HotelUsageController(hotelUsageService)
        mockMvc = MockMvcBuilders.standaloneSetup(testClass).build()
    }

    void cleanup() {
        mockMvc = null
        testClass = null
        objectMapper = null
        hotelUsageService = null
    }

    def "get hotel usage calculated results"() {

        given:
        def payload = new HotelUsageRequest(
                freePremiumRooms: 1,
                freeEconomyRooms: 1,
                potentialGuestPrices: prices
        )

        when:
        mockMvc.perform(MockMvcRequestBuilders.post("/hotel-usage/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())

        then:
        1 * hotelUsageService.calculateHotelUsage(
                payload.freePremiumRooms,
                payload.freeEconomyRooms,
                payload.potentialGuestPrices
        )
    }

    def "try to get hotel with invalid data supplied returns 400 http status"() {

        given:
        def payload = new HotelUsageRequest(
                freePremiumRooms: -1,
                freeEconomyRooms: -100,
                potentialGuestPrices: prices
        )

        when:
        mockMvc.perform(MockMvcRequestBuilders.post("/hotel-usage/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())

        then:
        0 * hotelUsageService.calculateHotelUsage(
                payload.freePremiumRooms,
                payload.freeEconomyRooms,
                payload.potentialGuestPrices
        )
    }
}
