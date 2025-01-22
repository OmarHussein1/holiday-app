package com.omar.holidayapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.omar.holidayapp.model.CommonHoliday;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayApiPort;
import com.omar.holidayapp.service.port.HolidayInputPort;

public class HolidayInputAdapter implements HolidayInputPort {

	private final HolidayApiPort holidayApiPort;

	public HolidayInputAdapter(HolidayApiPort holidayApiPort) {
		this.holidayApiPort = holidayApiPort;
	}

	@Override
	public List<Holiday> getLastThreeCelebratedHolidays(String country) {
		LocalDate now = LocalDate.now();
		List<Holiday> lastThreeHolidays =  new ArrayList<>();
		int year = now.getYear();
		while (lastThreeHolidays.size() < 3 && year > 0) {
			List<Holiday> publicHolidays =
					holidayApiPort.getPublicHolidays(country, year);
			List<Holiday> list = publicHolidays
					.stream()
					.filter(holiday -> holiday.date().isBefore(now))
					.toList();
			int size = list.size();
			if(size > 3) {
				lastThreeHolidays.addAll(list.subList(size - 3, size));
			} else {
				lastThreeHolidays.addAll(list.subList(0, size));
			}
			year --;
		}
		return lastThreeHolidays;
	}

	@Override
	public int getHolidayCountOutsideOfWeekend(String country, int year) {
		List<Holiday> publicHolidays =
				holidayApiPort.getPublicHolidays(country, year);
		return 0;
	}

	@Override
	public List<CommonHoliday> getCommonHolidays(String country1, String country2) {
		return List.of();
	}
}
