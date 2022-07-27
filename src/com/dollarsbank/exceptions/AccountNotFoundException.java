package com.dollarsbank.exceptions;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -4942887490882675994L;
	
	public AccountNotFoundException(int id) {
		super("Account with an ID of " + id + " not found.");
	}

}
