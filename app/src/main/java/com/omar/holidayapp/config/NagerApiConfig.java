package com.omar.holidayapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.omar.holidayclient.ApiClient;
import com.omar.holidayclient.client.CountryApi;
import com.omar.holidayclient.client.PublicHolidayApi;
import lombok.Getter;

@Getter
@Configuration
public class NagerApiConfig {

	@Value("${nager.api.url}")
	private String url;

	public PublicHolidayApi getHolidayApi(){
		return new PublicHolidayApi(getApiClient());
	}

	public CountryApi getCountryApi() {
		return new CountryApi(getApiClient());
	}

	private ApiClient getApiClient() {
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(url);
		return apiClient;
	}
}

