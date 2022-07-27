package com.dollarsbank.controller;

import java.util.List;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.CustomerNotFoundException;
import com.dollarsbank.model.Customer;

public interface CustomerManager {
	
	public List<Customer> getAllCustomers();
	
	public Customer getCustomerById(int id) throws CustomerNotFoundException;
	
	public boolean createCustomer(Customer cust);
	
	public boolean updateCustomer(Customer cust);
	
	public Customer getCustomerByCheckingId(int id) throws AccountNotFoundException;
	
	public Customer getCustomerBySavingsId(int id) throws AccountNotFoundException;

}