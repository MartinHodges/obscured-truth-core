package com.example.obscure.truth.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4240546934827596019L;
	public NotFoundException() {
        super();
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
