package com.example.obscure.truth.exceptions;

public class BadStateException extends RuntimeException {

	private static final long serialVersionUID = 4240546934827596019L;
	public BadStateException() {
        super();
    }
    public BadStateException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadStateException(String message) {
        super(message);
    }
    public BadStateException(Throwable cause) {
        super(cause);
    }
}
