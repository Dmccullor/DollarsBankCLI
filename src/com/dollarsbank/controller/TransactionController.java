package com.dollarsbank.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.TransactionNotFoundException;
import com.dollarsbank.model.Checking;
import com.dollarsbank.model.Savings;
import com.dollarsbank.model.Transaction;
import com.dollarsbank.application.Main.Type;
import com.dollarsbank.application.Main.ToAcct;

public class TransactionController implements TransactionManager {

	private static int idCounter = 0;
	private static List<Transaction> transactionList = new ArrayList<Transaction>();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	private static AccountManager checkingManager = new CheckingController();
	private static AccountManager savingsManager = new SavingsController();
	
	static {
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 1000.00, 1, 1, 0));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 9000.00, 1, 1, 0));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.SAVINGS, 10000.00, 1, 1, 1));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 1200.00, 2, 2, 0));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 3800.00, 2, 2, 2));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.SAVINGS, 15000.00, 2, 2, 2));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 500.00, 3, 3, 0));
		transactionList.add(new Transaction(idCounter++, LocalDateTime.now(), Type.DEPOSIT, ToAcct.CHECKING, 500.00, 3, 3, 0));
	}
	
	@Override
	public List<Transaction> getAllTransactions() {
		return transactionList;
	}

	@Override
	public Transaction findTransactionById(int id) throws TransactionNotFoundException {
		
		Optional<Transaction> tran = transactionList.stream()
				.filter((t) -> t.getId() == id)
				.findFirst();
		
		if(tran.isEmpty()) {
			throw new TransactionNotFoundException(id);
		}
		
		Transaction tranObj = tran.get();
		
		return tranObj;
	}

	@Override
	public boolean createTransaction(Transaction tran) throws AccountNotFoundException {
		
		tran.setId(idCounter++);
		
		transactionList.add(tran);
		
		if(tran.getType() == Type.DEPOSIT && tran.getToAcct() == ToAcct.CHECKING) {
			
			Checking currentAccount = (Checking) checkingManager.getAccountByUserId(tran.getUser_id());
			
			currentAccount.setAmount(currentAccount.getAmount() + tran.getAmount());
			
			checkingManager.updateAccount(currentAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has deposited $" + tran.getAmount() + " into their checking account.");
		}
		
		else if(tran.getType() == Type.DEPOSIT && tran.getToAcct() == ToAcct.SAVINGS) {
			
			Savings currentAccount = (Savings) savingsManager.getAccountByUserId(tran.getUser_id());
			
			currentAccount.setAmount(currentAccount.getAmount() + tran.getAmount());
			
			savingsManager.updateAccount(currentAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has deposited $" + tran.getAmount() + " into their savings account.");
		}
		
		else if(tran.getType() == Type.WITHDRAWAL && tran.getToAcct() == ToAcct.CHECKING) {
			
			Checking currentAccount = (Checking) checkingManager.getAccountByUserId(tran.getUser_id());
			
			currentAccount.setAmount(currentAccount.getAmount() + tran.getAmount());
			
			checkingManager.updateAccount(currentAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has withdrawn $" + tran.getAmount() + " from their checking account.");
		}
		
		else if(tran.getType() == Type.WITHDRAWAL && tran.getToAcct() == ToAcct.SAVINGS) {
			
			Savings currentAccount = (Savings) savingsManager.getAccountByUserId(tran.getUser_id());
			
			currentAccount.setAmount(currentAccount.getAmount() - tran.getAmount());
			
			savingsManager.updateAccount(currentAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has withdrawn $" + tran.getAmount() + " from their savings account.");
		}
		
		else if(tran.getType() == Type.TRANSFER && tran.getToAcct() == ToAcct.CHECKING) {
			
			Checking currentCheckingAccount = (Checking) checkingManager.getAccountByUserId(tran.getUser_id());
			
			Savings currentSavingsAccount = (Savings) savingsManager.getAccountByUserId(tran.getUser_id());
			
			currentCheckingAccount.setAmount(currentCheckingAccount.getAmount() + tran.getAmount());
			
			currentSavingsAccount.setAmount(currentSavingsAccount.getAmount() - tran.getAmount());
			
			checkingManager.updateAccount(currentCheckingAccount);
			
			savingsManager.updateAccount(currentSavingsAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has transferred $" + tran.getAmount() + " from their savings account to their checking account.");
		}
		
		else {
			
			Checking currentCheckingAccount = (Checking) checkingManager.getAccountByUserId(tran.getUser_id());
			
			Savings currentSavingsAccount = (Savings) savingsManager.getAccountByUserId(tran.getUser_id());
			
			currentCheckingAccount.setAmount(currentCheckingAccount.getAmount() - tran.getAmount());
			
			currentSavingsAccount.setAmount(currentSavingsAccount.getAmount() + tran.getAmount());
			
			checkingManager.updateAccount(currentCheckingAccount);
			
			savingsManager.updateAccount(currentSavingsAccount);
			
			System.out.println("Customer ID: " + tran.getUser_id() + " has transferred $" + tran.getAmount() + " from their checking acccount to their savings account.");
		}
		
		return false;
	}

	@Override
	public Transaction findTransactionByUserId(int id) throws TransactionNotFoundException {
		
		Optional<Transaction> tran = transactionList.stream()
				.filter((t) -> t.getUser_id() == id)
				.findFirst();
		
		if(tran.isEmpty()) {
			throw new TransactionNotFoundException(id);
		}
		
		Transaction tranObj = tran.get();
		
		return tranObj;
	}

}