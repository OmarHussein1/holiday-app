package com.omar.holidayapp.model;

import java.time.LocalDate;

public record Holiday(LocalDate date, String name, String localName) implements Comparable<Holiday> {

    public Holiday(LocalDate date, String name) {
        this(date, name, name);
    }

    @Override
    public int compareTo(Holiday o) {
        return date.compareTo(o.date);
    }
}
