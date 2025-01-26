package com.omar.holidayapp.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private int statusCode;
	public ApplicationException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public ApplicationException(String message, Throwable cause, int statusCode) {
		super(message, cause);
		this.statusCode = statusCode;
	}
}
