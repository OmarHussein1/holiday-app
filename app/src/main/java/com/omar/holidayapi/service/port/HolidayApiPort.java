package com.omar.holidayapi.service.port;

import java.util.List;

import com.omar.holidayapi.model.Country;
import com.omar.holidayapi.model.Holiday;

public interface HolidayApiPort {
	List<Holiday> getPublicHolidays(String country, int year);
	List<Holiday> getNextPublicHolidays(String country, int year, int month);
	List<Country> getCountries();
}
