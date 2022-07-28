package com.dollarsbank.controller;

import java.util.List;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.model.Account;

public interface AccountManager {
	
	public List<?> getAllAccounts();
	
	public Account findAccountById(int id) throws AccountNotFoundException;
	
	public boolean deleteAccount(int id);
	
	public Account getAccountByUserId(int id) throws AccountNotFoundException;
	
	public <A extends Account> boolean updateAccount(A acct);

	public <A extends Account> A createAccount(A acct);

}
