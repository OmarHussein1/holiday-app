package com.omar.holidayapp.controller;

import com.omar.holidayapp.exception.ApplicationException;
import com.omar.holidayapp.model.Holiday;
import com.omar.holidayapp.service.port.HolidayApiPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HolidayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HolidayApiPort holidayApiPort;

    private List<Holiday> holidaysNL;
    private List<Holiday> holidaysFR;

    @BeforeEach
    public void setUp() {
        holidaysNL = List.of(
                new Holiday(LocalDate.of(2024, 1, 1), "Nieuwjaarsdag"),
                new Holiday(LocalDate.of(2024, 3 , 29), "Goede Vrijdag"),
                new Holiday(LocalDate.of(2024, 3, 31), "Eerste Paasdag")
        );
        holidaysFR = List.of(
                new Holiday(LocalDate.of(2024, 1, 1), "Jour de lan"),
                new Holiday(LocalDate.of(2024, 3, 29), "Vendredi saint"),
                new Holiday(LocalDate.of(2024, 3, 31), "Pâques")
        );
    }



    @Test
    public void testGetRecentHolidaysWithInvalidCountry() throws Exception {
        when(holidayApiPort.getPublicHolidays(eq("XX"), anyInt()))
                .thenThrow(new ApplicationException("Invalid country", 400));
        mockMvc.perform(get("/api/v1/holidays/XX/recent"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRecentHolidays() throws Exception {
        when(holidayApiPort.getPublicHolidays(anyString(), anyInt()))
                .thenReturn(holidaysNL);
        mockMvc.perform(get("/api/v1/holidays/NL/recent"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'date':'2024-01-01','name':'Nieuwjaarsdag'},{'date':'2024-03-29','name':'Goede Vrijdag'},{'date':'2024-03-31','name':'Eerste Paasdag'}]"));
    }

    @Test
    public void testCountNonWeekendHolidays() throws Exception {
        when(holidayApiPort.getPublicHolidays(anyString(), anyInt()))
                .thenReturn(holidaysNL);

        mockMvc.perform(get("/api/v1/holidays/NL/year/2024/count-non-weekends"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void testGetCommonHolidaysWithInvalidYear() throws Exception {
        when(holidayApiPort.getPublicHolidays(anyString(), eq(0)))
                .thenThrow(new ApplicationException("Invalid year", 400));

        mockMvc.perform(get("/api/v1/holidays/compare")
                        .param("country1", "NL")
                        .param("country2", "FR")
                        .param("year", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCommonHolidaysWithValidYear() throws Exception {
        when(holidayApiPort.getPublicHolidays(eq("NL"), eq(2024)))
                .thenReturn(holidaysNL);
        when(holidayApiPort.getPublicHolidays(eq("FR"), eq(2024)))
                .thenReturn(holidaysFR);

        mockMvc.perform(get("/api/v1/holidays/compare")
                        .param("country1", "NL")
                        .param("country2", "FR")
                        .param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'date':'2024-01-01','nameInCountry1':'Nieuwjaarsdag','nameInCountry2':'Jour de lan'},{'date':'2024-03-29','nameInCountry1':'Goede Vrijdag','nameInCountry2':'Vendredi saint'},{'date':'2024-03-31','nameInCountry1':'Eerste Paasdag','nameInCountry2':'Pâques'}]"));
    }
}
