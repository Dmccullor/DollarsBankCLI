package com.dollarsbank.exceptions;

public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 121245718452374463L;
	
	public CustomerNotFoundException(int id) {
		super("Customer with ID: " + id + " not found.");
	}

}
