package com.dollarsbank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Checking;
import com.dollarsbank.model.Customer;

public class CheckingController implements AccountManager {

	private static int idCounter = 1;
	private static List<Checking> checkingList = new ArrayList<Checking>();
	
	static {		
		try {
			FileInputStream f = new FileInputStream("checking.txt");
			ObjectInputStream i = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			List<Checking> input = (List<Checking>) i.readObject();
			for (Checking cust : input) {
				checkingList.add(cust);
				idCounter++;
			}
			i.close();
			f.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Checking> getAllAccounts() {
		return checkingList;
	}

	@Override
	public Checking findAccountById(int id) throws AccountNotFoundException {
		
		Optional<Checking> acct = checkingList.stream()
				.filter((a) -> a.getId() == id)
				.findFirst();
		
		if(acct.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Checking acctObj = acct.get();
		
		return acctObj;
	}

	@Override
	public <A extends Account> A createAccount(A acct) {
		acct.setId(idCounter++);
		 
		checkingList.add((Checking) acct);
		
		System.out.println("User ID: " + acct.getUser_id() + " has a new checking account with $" + ((Checking) acct).getInit_deposit());
		System.out.println("Your new checking account has an ID of: " + acct.getId());
		 
		return acct;
	}

	@Override
	public boolean deleteAccount(int id) {
		
		int user_id = 0;
		
		for(Checking a: checkingList) {
			
			if(a.getId() == id) {
				
				user_id = a.getUser_id();
				
				checkingList.remove(a);
			}
		}
		
		System.out.println("Checking Account: " + id + " of Customer: " + user_id + " has been closed.");
		
		return false;
	}

	@Override
	public Checking getAccountByUserId(int id) throws AccountNotFoundException {
		
		Optional<Checking> acct = checkingList.stream()
				.filter((a) -> a.getUser_id() == id)
				.findFirst();
		
		if(acct.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Checking acctObj = acct.get();
		
		return acctObj;
	}

	@Override
	public <A extends Account> boolean updateAccount(A acct) {
		
		for(Checking a: checkingList) {
			
			if(a.getId() == acct.getId()) {
				
				checkingList.remove(a);
				
				checkingList.add((Checking) acct);
				
				System.out.println("Account Updated");
			}
		}
		
		return false;
	}

}
