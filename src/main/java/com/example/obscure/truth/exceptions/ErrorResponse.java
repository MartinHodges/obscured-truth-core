package com.example.obscure.truth.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;

@Data
public class ErrorResponse {

	private Date timestamp;
	private HttpStatus status;
	private String message;
	private String request;
	
	public ErrorResponse(Exception ex, HttpStatus status, String defaultMessage, WebRequest request) {
		timestamp = new Date();
		this.status = status;
		if (ex.getMessage() != null) {
			this.message = ex.getMessage();
		} else {
			this.message = defaultMessage;
		}
		this.request = request.toString();
	}
	
}
