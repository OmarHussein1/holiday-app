package com.omar.holidayapp.service;

import java.util.List;

import com.omar.holidayapp.config.NagerApiConfig;
import com.omar.holidayapp.exception.ApplicationException;
import com.omar.holidayapp.mapper.NagerMapper;
import com.omar.holidayapp.model.Country;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayApiPort;
import com.omar.holidayclient.ApiException;
import com.omar.holidayclient.client.CountryApi;
import com.omar.holidayclient.client.PublicHolidayApi;

public class NagerHolidayApiAdapter implements HolidayApiPort {

	private final NagerMapper mapper;
	private final PublicHolidayApi publicHolidayApi;
	private final CountryApi countryApi;


	public NagerHolidayApiAdapter(NagerMapper mapper, NagerApiConfig nagerApiConfig) {
		this.mapper = mapper;
		this.publicHolidayApi = nagerApiConfig.getHolidayApi();
		this.countryApi = nagerApiConfig.getCountryApi();
	}


	@Override
	public List<Holiday> getPublicHolidays(String country, int year) {
		try {
			return publicHolidayApi.publicHolidayPublicHolidaysV3(year, country)
					.stream()
					.map(mapper::toDomain)
					.toList();
		} catch (ApiException e) {
			throw new ApplicationException("Could not fetch public holidays, status code " + e.getCode(), e);
		}
	}

	@Override
	public List<Holiday> getNextPublicHolidays(String country) {
		try {
			return publicHolidayApi
					.publicHolidayNextPublicHolidays(country)
					.stream()
					.map(mapper::toDomain)
					.toList();
		} catch (ApiException e) {
			throw new ApplicationException("Could not fetch next public holiday, status code " + e.getCode(), e);
		}
	}

	@Override
	public List<Country> getCountries() {
		try {
			return countryApi
					.countryAvailableCountries()
					.stream()
					.map(mapper::toDomain)
					.toList();
		} catch (ApiException e) {
			throw new ApplicationException("Could not fetch available countries, status code" + e.getCode(), e);
		}
	}
}
