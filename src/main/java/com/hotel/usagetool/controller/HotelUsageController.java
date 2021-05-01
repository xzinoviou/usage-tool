package com.hotel.usagetool.controller;

import com.hotel.usagetool.domain.HotelUsage;
import com.hotel.usagetool.domain.HotelUsageRequest;
import com.hotel.usagetool.service.HotelUsageService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Hotel usage API .<br>
 * Exposes the operation for calculating a hotel usage.
 */
@RestController
@RequestMapping("/hotel-usage")
public class HotelUsageController {

  /**
   * The hotel usage service dependency.
   */
  private final HotelUsageService hotelUsageService;

  public HotelUsageController(HotelUsageService hotelUsageService) {
    this.hotelUsageService = hotelUsageService;
  }

  /**
   * Get a calculation of hotel usage based on a given free premium & economy rooms, <br>
   * and a list of potential guest prices.
   *
   * @param request the hotel usage request.
   * @return the hotel usage result.
   */
  @PostMapping("/calculate")
  public ResponseEntity<HotelUsage> getCalculatedHotelUsage(
      @Valid @RequestBody HotelUsageRequest request) {
    return ResponseEntity.ok(hotelUsageService.calculateHotelUsage(request.getFreePremiumRooms(),
        request.getFreeEconomyRooms(), request.getPotentialGuestPrices()));
  }

}
