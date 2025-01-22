package com.omar.holidayapp.service.port;

import java.util.List;

import com.omar.holidayapp.model.Country;
import com.omar.holidayapp.model.Holiday;

public interface HolidayApiPort {
	List<Holiday> getPublicHolidays(String country, int year);
	List<Holiday> getNextPublicHolidays(String country);
	List<Country> getCountries();
}
