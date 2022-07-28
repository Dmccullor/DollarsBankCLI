package com.dollarsbank.exceptions;

public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = -5020671405216672492L;
	
	public InvalidCredentialsException(int id) {
		super("User ID: " + id + " not found or password doesn't match. Please try again");
	}

}
