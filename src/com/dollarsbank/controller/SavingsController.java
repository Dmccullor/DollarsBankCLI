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
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Savings;

public class SavingsController implements AccountManager {

	private static int idCounter = 1;
	private static List<Savings> savingsList = new ArrayList<Savings>();
	
	static {
		try {
			FileInputStream f = new FileInputStream("savings.txt");
			ObjectInputStream i = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			List<Savings> input = (List<Savings>) i.readObject();
			for (Savings cust : input) {
				savingsList.add(cust);
				idCounter++;
			}
			i.close();
			f.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public List<Savings> getAllAccounts() {
		return savingsList;
	}

	@Override
	public Savings findAccountById(int id) throws AccountNotFoundException {
		
		Optional<Savings> acct = savingsList.stream()
				.filter((a) -> a.getId() == id)
				.findFirst();
		
		if(acct.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Savings acctObj = acct.get();
		
		return acctObj;
	}

	@Override
	public <A extends Account> A createAccount(A acct) {
		acct.setId(idCounter++);
		
		savingsList.add((Savings) acct);
		
		System.out.println("User ID: " + acct.getUser_id() + " has a new savings account with an ID of " + acct.getId());
		
		return acct;
	}

	@Override
	public boolean deleteAccount(int id) {
		
		int user_id = 0;
		
		for(Savings a: savingsList) {
			
			if(a.getId() == id) {
				
				user_id = a.getUser_id();
				
				savingsList.remove(a);
			}
		}
		
		System.out.println("Savings Account: " + id + " of Customer: " + user_id + " has been closed.");
		
		return false;
	}

	@Override
	public Savings getAccountByUserId(int id) throws AccountNotFoundException {
		
		Optional<Savings> acct = savingsList.stream()
				.filter((a) -> a.getUser_id() == id)
				.findFirst();
		
		if(acct.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Savings acctObj = acct.get();
		
		return acctObj;
	}
	
	public Savings getAccountByCheckingId(int id) throws AccountNotFoundException {
		
		Optional<Savings> acct = savingsList.stream()
				.filter((a) -> a.getChecking_id() == id)
				.findFirst();
		
		if(acct.isEmpty()) {
			throw new AccountNotFoundException(id);
		}
		
		Savings acctObj = acct.get();
		
		return acctObj;
	}

	@Override
	public <A extends Account> boolean updateAccount(A acct) {
		// TODO Auto-generated method stub
			for(Savings a: savingsList) {
			
			if(a.getId() == acct.getId()) {
				
				savingsList.remove(a);
				
				savingsList.add((Savings) acct);
				
				System.out.println("Account updated");
			}
		}
		return false;
	}

}
