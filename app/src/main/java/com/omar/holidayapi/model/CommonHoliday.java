package com.omar.holidayapi.model;

import java.time.LocalDate;

public record CommonHoliday(LocalDate date, String nameInCountry1, String nameInCountry2) {
}
