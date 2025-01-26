package com.omar.holidayapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Holiday API",
                version = "1.0",
                description = "API for managing holidays"
        )
)
@SpringBootApplication
public class HolidayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayApiApplication.class, args);
    }
}
