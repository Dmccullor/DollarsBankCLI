package com.dollarsbank.controller;

import java.util.List;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.TransactionNotFoundException;
import com.dollarsbank.model.Transaction;

public interface TransactionManager {
	
	public List<Transaction> getAllTransactions();
	
	public Transaction findTransactionById(int id) throws TransactionNotFoundException;
	
	public boolean createTransaction(Transaction t) throws AccountNotFoundException;
	
	public List<Transaction> findTransactionsByUserId(int id);

}
