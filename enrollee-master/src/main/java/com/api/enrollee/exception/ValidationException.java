package com.api.enrollee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -1410395468483938622L;
	private String message;

	public ValidationException(String message) {
		super(message);
		this.message = message;
	}
}
