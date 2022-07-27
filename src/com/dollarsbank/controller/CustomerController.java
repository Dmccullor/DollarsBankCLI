package com.dollarsbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.CustomerNotFoundException;
import com.dollarsbank.model.Customer;



public class CustomerController implements CustomerManager{
	
	private static int idCounter = 0;
	private static List<Customer> customerList = new ArrayList<Customer>();
	
	static {
		customerList.add(new Customer(idCounter++, "Mary Beth", "123 Pine St.", 3105768977L, "pass123", 1, true, 1));
		customerList.add(new Customer(idCounter++, "John Doe", "827 New Haven Dr.", 9726985644L, "pass123", 2, true, 2));
		customerList.add(new Customer(idCounter++, "Trey Songz", "1422 Queens Ave.", 2108675309L, "pass123", 3, false, 0));
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return customerList;
	}

	@Override
	public Customer getCustomerById(int id) throws CustomerNotFoundException {
		
		Optional<Customer> cust = customerList.stream()
				.filter((e) -> e.getId() == id)
				.findFirst();
		
		if(cust.isEmpty()) {
			throw new CustomerNotFoundException(id);
		}
		
		Customer custObj = cust.get();
		
		return custObj;
	}

	@Override
	public boolean createCustomer(Customer cust) {
		
		cust.setId(idCounter++);
		
		customerList.add(cust);
		
		System.out.println(cust.getName() + " has created an Account!");
		
		return false;
	}

	@Override
	public boolean updateCustomer(Customer cust) {
		
		for(Customer c: customerList) {
			
			if(c.getId() == cust.getId()) {
				
				customerList.remove(c);
				customerList.add(cust);
				System.out.println("Customer " + cust.getName() + " has been updated.");
			}
		}
		return false;
	}


	@Override
	public Customer getCustomerByCheckingId(int id) throws AccountNotFoundException {
		
		Optional<Customer> cust = customerList.stream()
				.filter((c) -> c.getChecking_id() == id)
				.findFirst();
		
		if(cust.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Customer custObj = cust.get();
		
		return custObj;
	}

	@Override
	public Customer getCustomerBySavingsId(int id) throws AccountNotFoundException {
		
		Optional<Customer> cust = customerList.stream()
				.filter((c) -> c.getSavings_id() == id)
				.findFirst();
		
		if(cust.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Customer custObj = cust.get();
		
		return custObj;
	}

}
