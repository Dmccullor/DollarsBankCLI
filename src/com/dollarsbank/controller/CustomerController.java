package com.dollarsbank.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.CustomerNotFoundException;
import com.dollarsbank.model.Customer;



public class CustomerController implements CustomerManager{
	
	private static int idCounter = 1;
	private static List<Customer> customerList = new ArrayList<Customer>();
	
	static {		
		try {
			FileInputStream f = new FileInputStream("customers.txt");
			ObjectInputStream i = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			List<Customer> input = (List<Customer>) i.readObject();
			for (Customer cust : input) {
				customerList.add(cust);
			}
			i.close();
			f.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	public Customer createCustomer(Customer cust) {
		
		cust.setId(idCounter++);
		
		customerList.add(cust);
		
		System.out.println(cust.getName() + " has created an Account!");
		System.out.println("Your new ID number is : " + cust.getId());
		
		return cust;
	}

	@Override
	public boolean updateCustomer(Customer cust) {
		
		for(Customer c: customerList) {
			
			if(c.getId() == cust.getId()) {
				
				customerList.remove(c);
				customerList.add(cust);
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
