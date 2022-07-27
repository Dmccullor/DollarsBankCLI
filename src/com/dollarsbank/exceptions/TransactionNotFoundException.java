package com.dollarsbank.exceptions;

public class TransactionNotFoundException extends Exception {

	private static final long serialVersionUID = 6647009048429762585L;
	
	public TransactionNotFoundException(int id) {
		super("Transaction ID: " + id + " not found.");
	}

}
