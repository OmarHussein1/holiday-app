package com.omar.holidayapp.controller;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayInputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for managing holiday-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayController {

    private final HolidayInputPort holidayService;

    public HolidayController(HolidayInputPort holidayService) {
        this.holidayService = holidayService;
    }

    /**
     * Retrieves the last three holidays celebrated in a given country before the specified date.
     *
     * @param country     the country code (e.g., "US", "FR").
     * @return a list of the last three celebrated holidays.
     */
    @GetMapping("/{country}/recent")
    public List<Holiday> getRecentHolidays(
            @PathVariable String country) {
        return holidayService.getLastThreeCelebratedHolidays(country, LocalDate.now());
    }

    /**
     * Counts the number of holidays in a given year that do not fall on weekends for a specific country.
     *
     * @param country the country code (e.g., "US", "FR").
     * @param year    the year for which to count holidays.
     * @return the number of holidays that are not on weekends.
     */
    @GetMapping("/{country}/year/{year}/count-non-weekends")
    public int countNonWeekendHolidays(
            @PathVariable String country,
            @PathVariable int year) {
        return holidayService.getHolidayCountOutsideOfWeekend(country, year);
    }

    /**
     * Retrieves a list of holidays that are common between two countries for a specific year.
     *
     * @param country1 the first country code.
     * @param country2 the second country code.
     * @param year     the year for comparison.
     * @return a list of common holidays.
     */
    @GetMapping("/compare")
    public List<CommonHoliday> getCommonHolidays(
            @RequestParam String country1,
            @RequestParam String country2,
            @RequestParam int year) {
        return holidayService.getCommonHolidays(country1, country2, year);
    }
}
