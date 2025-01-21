package com.omar.holidayapi.service.port;

import java.util.List;

import com.omar.holidayapi.model.CommonHoliday;
import com.omar.holidayapi.model.Holiday;

public interface HolidayInputPort {

	List<Holiday> getLastThreeCelebratedHolidays(String country);
	int getHolidayCountOutsideOfWeekend(String country);
	List<CommonHoliday> getCommonHolidays(String country1, String country2);

}
