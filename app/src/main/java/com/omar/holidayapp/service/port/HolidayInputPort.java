package com.omar.holidayapp.service.port;

import java.util.List;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;

public interface HolidayInputPort {

	List<Holiday> getLastThreeCelebratedHolidays(String country);
	int getHolidayCountOutsideOfWeekend(String country);
	List<CommonHoliday> getCommonHolidays(String country1, String country2);

}
