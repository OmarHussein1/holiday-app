package com.omar.holidayapp.service.port;

import java.time.LocalDate;
import java.util.List;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;

public interface HolidayInputPort {

	List<Holiday> getLastThreeCelebratedHolidays(String country, LocalDate currentDate);
	int getHolidayCountOutsideOfWeekend(String country, int year);
	List<CommonHoliday> getCommonHolidays(String country1, String country2, int year);

}
