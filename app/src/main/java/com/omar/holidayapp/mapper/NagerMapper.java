package com.omar.holidayapp.mapper;

import org.mapstruct.Mapper;

import com.omar.holidayapp.model.Country;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayclient.model.CountryV3Dto;
import com.omar.holidayclient.model.PublicHolidayV3Dto;

@Mapper(componentModel = "spring")
public interface NagerMapper {

	Country toDomain(CountryV3Dto countryV3Dto);
	Holiday toDomain(PublicHolidayV3Dto publicHolidayV3Dto);
}
