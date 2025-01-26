package com.omar.holidayapp.service;

import java.time.LocalDate;
import java.util.List;

import com.omar.holidayapp.model.CommonHoliday;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayApiPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolidayInputAdapterTest {

    @Mock
    HolidayApiPort holidayApiPort;

    @InjectMocks
    HolidayInputAdapter holidayInputAdapter;

    @Test
    void getLastThreeCelebratedHolidays_whenMoreThanThreeHolidaysCelebratedThisYear_returnThreeFromThisYear() {
        LocalDate now = LocalDate.of(2025, 12, 1);
        Holiday firstHoliday = new Holiday(LocalDate.of(2025, 1, 1), "First Holiday");
        Holiday secondHoliday = new Holiday(LocalDate.of(2025, 2, 2), "Second Holiday");
        Holiday thirdHoliday = new Holiday(LocalDate.of(2025, 3, 17), "Third Holiday");
        Holiday fourthHoliday = new Holiday(LocalDate.of(2025, 11, 12), "Fourth holiday");
        List<Holiday> publicHolidays = List.of(firstHoliday,
                secondHoliday,
                thirdHoliday,
                fourthHoliday);
        when(holidayApiPort.getPublicHolidays("NL", now.getYear())).thenReturn(publicHolidays);

        List<Holiday> lastThreeCelebratedHolidays =
                holidayInputAdapter.getLastThreeCelebratedHolidays("NL", now);
        assertEquals(3, lastThreeCelebratedHolidays.size());
        assertEquals(secondHoliday, lastThreeCelebratedHolidays.get(0));
        assertEquals(thirdHoliday, lastThreeCelebratedHolidays.get(1));
        assertEquals(fourthHoliday, lastThreeCelebratedHolidays.get(2));
    }

    @Test
    void getLastThreeCelebratedHolidays_whenLessThanThreeHolidaysCelebratedThisYear_returnMaxFromThisYearAndRestFromPrev() {
        LocalDate now = LocalDate.of(2025, 1, 2);
        Holiday firstHoliday2024 = new Holiday(LocalDate.of(2024, 1, 1), "First Holiday 2024");
        Holiday beforeLastHoliday2024 = new Holiday(LocalDate.of(2024, 11, 1), "Before Last Holiday 2024");
        Holiday lastHoliday2024 = new Holiday(LocalDate.of(2024, 12, 1), "Last Holiday 2024");
        Holiday firstHoliday2025 = new Holiday(LocalDate.of(2025, 1, 1), "First Holiday 2025");
        List<Holiday> publicHolidays2025 = List.of(firstHoliday2025);
        List<Holiday> publicHolidays2024 = List.of(firstHoliday2024, beforeLastHoliday2024, lastHoliday2024);

        when(holidayApiPort.getPublicHolidays("NL", now.getYear())).thenReturn(publicHolidays2025);
        when(holidayApiPort.getPublicHolidays("NL", now.getYear() - 1)).thenReturn(publicHolidays2024);

        List<Holiday> lastThreeCelebratedHolidays =
                holidayInputAdapter.getLastThreeCelebratedHolidays("NL", now);
        assertEquals(3, lastThreeCelebratedHolidays.size());
        assertEquals(beforeLastHoliday2024, lastThreeCelebratedHolidays.get(0));
        assertEquals(lastHoliday2024, lastThreeCelebratedHolidays.get(1));
        assertEquals(firstHoliday2025, lastThreeCelebratedHolidays.get(2));
    }

    @Test
    void getHolidayCountOutsideOfWeekend_whenAllHolidaysOnWeekdays_returnCorrectCount() {
        List<Holiday> publicHolidays = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Day"),
                new Holiday(LocalDate.of(2025, 2, 3), "Second Holiday"),
                new Holiday(LocalDate.of(2025, 3, 3), "Third Holiday")
        );
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(publicHolidays);

        int count = holidayInputAdapter.getHolidayCountOutsideOfWeekend("US", 2025);
        assertEquals(3, count);
    }

    @Test
    void getHolidayCountOutsideOfWeekend_whenSomeHolidaysOnWeekends_returnCorrectCount() {
        List<Holiday> publicHolidays = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Day"),
                new Holiday(LocalDate.of(2025, 2, 1), "Weekend Holiday"),
                new Holiday(LocalDate.of(2025, 3, 3), "Third Holiday")
        );
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(publicHolidays);

        int count = holidayInputAdapter.getHolidayCountOutsideOfWeekend("US", 2025);
        assertEquals(2, count);
    }

    @Test
    void getHolidayCountOutsideOfWeekend_whenAllHolidaysOnWeekends_returnZero() {
        List<Holiday> publicHolidays = List.of(
                new Holiday(LocalDate.of(2025, 1, 4), "Weekend Holiday 1"),
                new Holiday(LocalDate.of(2025, 2, 1), "Weekend Holiday 2")
        );
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(publicHolidays);

        int count = holidayInputAdapter.getHolidayCountOutsideOfWeekend("US", 2025);
        assertEquals(0, count);
    }

    @Test
    void getHolidayCountOutsideOfWeekend_whenNoHolidays_returnZero() {
        List<Holiday> publicHolidays = List.of();
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(publicHolidays);

        int count = holidayInputAdapter.getHolidayCountOutsideOfWeekend("US", 2025);
        assertEquals(0, count);
    }

    @Test
    void getCommonHolidays_whenCommonHolidaysExist_returnCommonHolidays() {
        List<Holiday> holidaysCountry1 = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Day"),
                new Holiday(LocalDate.of(2025, 3, 3), "Third Holiday")
        );
        List<Holiday> holidaysCountry2 = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Dag"),
                new Holiday(LocalDate.of(2025, 4, 4), "Fourth Holiday")
        );
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(holidaysCountry1);
        when(holidayApiPort.getPublicHolidays("NL", 2025)).thenReturn(holidaysCountry2);

        List<CommonHoliday> commonHolidays = holidayInputAdapter.getCommonHolidays("US", "NL", 2025);
        assertEquals(1, commonHolidays.size());
        assertEquals("New Year's Day", commonHolidays.getFirst().nameInCountry1());
        assertEquals("New Year's Dag", commonHolidays.getFirst().nameInCountry2());
    }

    @Test
    void getCommonHolidays_whenNoCommonHolidays_returnEmptyList() {
        List<Holiday> holidaysCountry1 = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Day"),
                new Holiday(LocalDate.of(2025, 3, 3), "Third Holiday")
        );
        List<Holiday> holidaysCountry2 = List.of(
                new Holiday(LocalDate.of(2025, 4, 4), "Fourth Holiday"),
                new Holiday(LocalDate.of(2025, 5, 5), "Fifth Holiday")
        );
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(holidaysCountry1);
        when(holidayApiPort.getPublicHolidays("CA", 2025)).thenReturn(holidaysCountry2);

        List<CommonHoliday> commonHolidays = holidayInputAdapter.getCommonHolidays("US", "CA", 2025);
        assertTrue(commonHolidays.isEmpty());
    }

    @Test
    void getCommonHolidays_whenOneCountryHasNoHolidays_returnEmptyList() {
        List<Holiday> holidaysCountry1 = List.of(
                new Holiday(LocalDate.of(2025, 1, 1), "New Year's Day"),
                new Holiday(LocalDate.of(2025, 3, 3), "Third Holiday")
        );
        List<Holiday> holidaysCountry2 = List.of();
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(holidaysCountry1);
        when(holidayApiPort.getPublicHolidays("CA", 2025)).thenReturn(holidaysCountry2);

        List<CommonHoliday> commonHolidays = holidayInputAdapter.getCommonHolidays("US", "CA", 2025);
        assertTrue(commonHolidays.isEmpty());
    }

    @Test
    void getCommonHolidays_whenBothCountriesHaveNoHolidays_returnEmptyList() {
        List<Holiday> holidaysCountry1 = List.of();
        List<Holiday> holidaysCountry2 = List.of();
        when(holidayApiPort.getPublicHolidays("US", 2025)).thenReturn(holidaysCountry1);
        when(holidayApiPort.getPublicHolidays("CA", 2025)).thenReturn(holidaysCountry2);

        List<CommonHoliday> commonHolidays = holidayInputAdapter.getCommonHolidays("US", "CA", 2025);
        assertTrue(commonHolidays.isEmpty());
    }

}
