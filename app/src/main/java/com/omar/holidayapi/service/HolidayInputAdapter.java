package com.omar.holidayapi.service;

import java.util.List;

import com.omar.holidayapi.model.CommonHoliday;
import com.omar.holidayapi.model.Holiday;
import com.omar.holidayapi.service.port.HolidayInputPort;

public class HolidayInputAdapter implements HolidayInputPort {

	@Override
	public List<Holiday> getLastThreeCelebratedHolidays(String country) {
		return List.of();
	}

	@Override
	public int getHolidayCountOutsideOfWeekend(String country) {
		return 0;
	}

	@Override
	public List<CommonHoliday> getCommonHolidays(String country1, String country2) {
		return List.of();
	}
}
