package com.omar.holidayapp.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayApiPort;
import com.omar.holidayapp.service.port.HolidayInputPort;
import org.springframework.stereotype.Service;

@Service
public class HolidayInputAdapter implements HolidayInputPort {

    private final HolidayApiPort holidayApiPort;

    public HolidayInputAdapter(HolidayApiPort holidayApiPort) {
        this.holidayApiPort = holidayApiPort;
    }

    @Override
    public List<Holiday> getLastThreeCelebratedHolidays(String country, LocalDate currentDate) {
        List<Holiday> lastThreeHolidays = new ArrayList<>();
        int year = currentDate.getYear();

        while (lastThreeHolidays.size() < 3 && year > 0) {
            List<Holiday> publicHolidays = holidayApiPort.getPublicHolidays(country, year);
            List<Holiday> filteredHolidays = publicHolidays.stream()
                    .filter(holiday -> holiday.date().isBefore(currentDate))
                    .toList();
            int holidaysToAdd = Math.min(3 - lastThreeHolidays.size(), filteredHolidays.size());
            lastThreeHolidays
                    .addAll(filteredHolidays.subList(filteredHolidays.size() - holidaysToAdd, filteredHolidays.size()));
            year--;
        }
        lastThreeHolidays.sort(Comparator.comparing(Holiday::date));
        return lastThreeHolidays;
    }

    @Override
    public int getHolidayCountOutsideOfWeekend(String country, int year) {
        List<Holiday> publicHolidays = holidayApiPort.getPublicHolidays(country, year);
        // In the future would be interesting to explore adjust the weekends based on the country
        long count = publicHolidays.stream()
                .filter(holiday -> {
                    LocalDate date = holiday.date();
                    return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;
                })
                .count();
        return (int) count;
    }

    @Override
    public List<CommonHoliday> getCommonHolidays(String country1, String country2, int year) {
        Map<LocalDate, Holiday> publicHolidays1Map =
                holidayApiPort.getPublicHolidays(country1, year)
                        .stream()
                        .collect(
                                Collectors
                                        .toMap(Holiday::date,
                                                Function.identity(),
                                                ((holiday1, holiday2) -> holiday1)));
        List<Holiday> publicHolidays2 = holidayApiPort.getPublicHolidays(country2, year);

        return publicHolidays2.stream()
                .filter(holiday -> publicHolidays1Map.containsKey(holiday.date()))
                .map(holiday ->
                        new CommonHoliday(
                                holiday.date(),
                                publicHolidays1Map.get(holiday.date()).localName(),
                                holiday.localName()))
                .distinct()
                .collect(Collectors.toList());
    }
}
