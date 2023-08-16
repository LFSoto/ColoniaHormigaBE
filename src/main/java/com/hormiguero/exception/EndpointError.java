package com.hormiguero.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class EndpointError {
	
	private HttpStatus status;
    private String message;
    private List<String> errors;

    public EndpointError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public EndpointError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

	public HttpStatusCode getStatus() {
		return this.status;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
}
