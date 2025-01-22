package com.omar.holidayapp.model;

import java.time.LocalDate;

public record Holiday(LocalDate date, String name) implements Comparable<Holiday> {

	@Override
	public int compareTo(Holiday o) {
		return date.compareTo(o.date);
	}
}
