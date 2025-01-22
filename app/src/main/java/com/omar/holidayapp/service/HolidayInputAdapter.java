package com.omar.holidayapp.service;

import java.util.List;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayInputPort;

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
