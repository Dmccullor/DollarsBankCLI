package com.dollarsbank.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.dollarsbank.controller.AccountManager;
import com.dollarsbank.controller.CheckingController;
import com.dollarsbank.controller.CustomerController;
import com.dollarsbank.controller.CustomerManager;
import com.dollarsbank.controller.SavingsController;
import com.dollarsbank.controller.TransactionController;
import com.dollarsbank.controller.TransactionManager;
import com.dollarsbank.exceptions.AccountNotFoundException;
import com.dollarsbank.exceptions.InvalidCredentialsException;
import com.dollarsbank.model.Checking;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Savings;
import com.dollarsbank.model.Transaction;

public class Main {

	public enum Type {
		DEPOSIT, WITHDRAWAL, TRANSFER
	}
	
	public enum ToAcct {
		CHECKING, SAVINGS
	}
	
	private static CustomerManager manager;
	private static AccountManager checkingManager;
	private static AccountManager savingsManager;
	private static TransactionManager transactionManager;
	private static Scanner sc;
	private static Customer principal;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	public static void main(String[] args) {
		
		manager = new CustomerController();
		checkingManager = new CheckingController();
		savingsManager = new SavingsController();
		transactionManager = new TransactionController();
		sc = new Scanner(System.in);
		
		System.out.println("WELCOME TO DOLLARS BANK");
		
		while(true) {
			
			try {
				
				System.out.println("\nPlease enter one of the following options:"
						+ "\n1.) Create New Account"
						+ "\n2.) Login to Existing Account"
						+ "\n3.) Exit");
				
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
				case 1:
					createCustomer();
					break;
				case 2:
					loginUser();
				case 3:
					break;
					
				default:
					System.out.println("\nPlease enter a number between 1 and 3");
				}
				
				if (option == 3) {
					break;
				}
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("\nPlease enter a number between 1 and 3");
			}
		}
	}
	
	
	public static void createCustomer() {
		
		try {
			System.out.println("Please enter your full name:");
			String name = sc.nextLine();
			
			System.out.println("Please enter your address:");
			String address = sc.nextLine();
			
			System.out.println("Please enter your phone number (with no spaces):");
			long phone = sc.nextLong();
			sc.nextLine();
			
			System.out.println("Please enter a password for your account:");
			String password = sc.nextLine();
			
			Customer newCust = new Customer(0, name, address, phone, password, 0, false, 0);
			principal = manager.createCustomer(newCust);
			
			
			createCheckingAccount();
			
			
		} catch(InputMismatchException e) {
			
			System.out.println("Input Invalid. Please start over and try again.");
			sc.next();
			createCustomer();
		}
		
	}
	
	public static void createCheckingAccount() {
		
		try {
			int user_id = principal.getId();
			
			System.out.println("\n\nNew accounts require an initial deposit of at least $500. How much would you like to deposit?");
			double init_amount = sc.nextDouble();
			
			if(init_amount < 500.00) {
				System.out.println("Initial deposit must be at leasst $500.00");
				createCheckingAccount();
			}
			
			Checking newChecking = new Checking(0, 0, init_amount, user_id, 0);
			Checking newAcct = checkingManager.createAccount(newChecking);
			principal.setChecking_id(newAcct.getId());
			manager.updateCustomer(principal);
			
			
			int checking_id = principal.getChecking_id();
			Type type = Type.DEPOSIT;
			ToAcct toAcct = ToAcct.CHECKING;
			int savings_id = 0;
			
			Transaction tran = new Transaction(0, LocalDateTime.now(), type, toAcct, init_amount, user_id, checking_id, savings_id);
			transactionManager.createTransaction(tran);
			
			loginUser();
			
		} catch(InputMismatchException e) {
			System.out.println("Input Invalid. Please start over and try again.");
			sc.next();
			createCheckingAccount();
		} catch (AccountNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static void loginUser() {
		
		System.out.println("\n\nCUSTOMER LOGIN");
		
		try {
			System.out.println("Enter your Customer ID:");
			int user_id = sc.nextInt();
			sc.nextLine();
			
			System.out.println("Enter your password:");
			String password = sc.nextLine();
			
			List<Customer> customerList = manager.getAllCustomers();
			
			Optional<Customer> cust = customerList.stream()
					.filter((c) -> (c.getId() == user_id && c.getPassword().equals(password)))
					.findFirst();
			
			if(cust.isEmpty()) {
				throw new InvalidCredentialsException(user_id);
			}
			else {
				principal = cust.get();
				System.out.println("Login Success!");
				userMenu();
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Input is invalid. Please try again.");
			sc.next();
			loginUser();
		} catch (InvalidCredentialsException e) {
			e.getMessage();
		}
	}
	
	public static void userMenu() {
		System.out.println("Welcome " + principal.getName() + "!");
		
		while(true) {
			try {
				System.out.println("\nCUSTOMER MENU" + 
						"\nSelect one of the following:" +
						"\n1.) Make a deposit" +
						"\n2.) Make a withdrawal" +
						"\n3.) Open a savings account" +
						"\n4.) Transfer funds" + 
						"\n5.) View 5 recent transactions" +
						"\n6.) Display customer information" +
						"\n7.) Logout");
				
				int option = sc.nextInt();
				sc.nextLine();
				
				switch(option) {
				case 1:
					makeDeposit();
					break;
				case 2:
					makeWithdrawal();
					break;
				case 3:
					openSavings();
					break;
				case 4:
					transferFunds();
					break;
				case 5:
					viewTransactions();
					break;
				case 6:
					showInfo();
					break;
				case 7:
					main(null);
					break;
				default:
					System.out.println("Please enter a number between 1 and 7");
					break;
				}
				
				if(option == 3) {
					break;
				}
				
				
			} catch(InputMismatchException e) {
				System.out.println("Input is invalid. Please try again.");
				sc.next();
				userMenu();
			} catch(ConcurrentModificationException e) {
				
			}
		}
		
	}
	
	public static void makeDeposit() {
		try {
			int checking_id = principal.getChecking_id();
			int user_id = principal.getId();
			Type type = Type.DEPOSIT;
			ToAcct toAcct = null;
			int savings_id = 0;
			
			if(principal.getHas_savings() == false) {
				toAcct = ToAcct.CHECKING;
			}
			else {
				System.out.println("To which account would you like to make a deposit?" +
						"\n1.) Checking" +
						"\n2.) Savings" +
						"\n3.) Back");
				
				int selection = sc.nextInt();
				sc.nextLine();
				
				if(selection == 1) {
					toAcct = ToAcct.CHECKING;
					savings_id = principal.getSavings_id();
				}
				else if(selection == 2) {
					toAcct = ToAcct.SAVINGS;
					savings_id = principal.getSavings_id();
				}
				else if(selection ==3) {
					userMenu();
				}
				else {
					System.out.println("You must either select 1, 2 or 3.");
					makeDeposit();
				}
			}
			
			System.out.println("How much would you like to deposit?");
			Double amount = sc.nextDouble();
			sc.nextLine();
			
			if(amount <= 0) {
				System.out.println("Amount must be greater than 0");
				makeDeposit();
			}
			
			Transaction tran = new Transaction(0, LocalDateTime.now(), type, toAcct, amount, user_id, checking_id, savings_id);
			transactionManager.createTransaction(tran);
			System.out.println("Deposit initiated!");
			
		} catch(InputMismatchException e) {
			System.out.println("Input invalid. Please try again.");
			sc.nextLine();
			makeDeposit();
		} catch (AccountNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static void makeWithdrawal() {
		try {
			int checking_id = principal.getChecking_id();
			int user_id = principal.getId();
			Type type = Type.WITHDRAWAL;
			ToAcct toAcct = null;
			int savings_id = 0;
			double accountBalance = 0;
			
			if(principal.getHas_savings() == false) {
				toAcct = ToAcct.CHECKING;
				accountBalance = (checkingManager.getAccountByUserId(user_id)).getAmount();
			}
			else {
				savings_id = principal.getSavings_id();
				System.out.println("To which account would you like to make a withdrawal?" +
						"\n1.) Checking" +
						"\n2.) Savings" +
						"\n3.) Back");
				
				int selection = sc.nextInt();
				sc.nextLine();
				
				if(selection == 1) {
					toAcct = ToAcct.CHECKING;
					accountBalance = (checkingManager.getAccountByUserId(user_id)).getAmount();
				}
				else if(selection == 2) {
					toAcct = ToAcct.SAVINGS;
					accountBalance = (savingsManager.getAccountByUserId(user_id)).getAmount();
				}
				else if(selection ==3) {
					userMenu();
				}
				else {
					System.out.println("You must either select 1, 2 or 3.");
					makeWithdrawal();
				}
			}
			
			System.out.println("How much do you want to withdraw?");
			double amount = sc.nextDouble();
			
			if(amount > accountBalance) {
				System.out.println("You don't have enough money in your account. Your children are now in the custody of DollarsBank.");
				userMenu();
			}
			else if(amount <= 0) {
				System.out.println("Amount must be greater than 0");
			}
			else {
				Transaction tran = new Transaction(0, LocalDateTime.now(), type, toAcct, amount, user_id, checking_id, savings_id);
				transactionManager.createTransaction(tran);
				System.out.println("Withdrawal initiated!");
			}
			
			
		} catch(InputMismatchException e) {
			System.out.println("Input invalid. Please start over and try again.");
			sc.next();
		} catch (AccountNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static void openSavings() {
		try {
			if(principal.getHas_savings() == true) {
				System.out.println("You already have a savings account. Returning to main menu");
				userMenu();
			}
			else {
				System.out.println("Are you sure you want to open a savings account? Enter 1 for yes and 2 for no");
				int selection = sc.nextInt();
				
				if(selection == 1) {
					Savings savingsAcct = new Savings(0, 0, principal.getId(), principal.getChecking_id());
					Savings newAcct = savingsManager.createAccount(savingsAcct);
					principal.setSavings_id(newAcct.getId());
					principal.setHas_savings(true);
					manager.updateCustomer(principal);
					userMenu();
				}
				else if(selection == 2) {
					System.out.println("Returning to main menu.");
					userMenu();
				}
				else {
					System.out.println("You must either select 1 or 2.");
					openSavings();
				}
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Input invalid. Please start over and try again.");
			sc.next();
			makeWithdrawal();
		}
	}
	
	public static void transferFunds() {
		try {
			int checking_id = principal.getChecking_id();
			int user_id = principal.getId();
			Type type = Type.TRANSFER;
			ToAcct toAcct = null;
			int savings_id = 0;
			double accountBalance = 0;
			
			if(principal.getHas_savings() == false) {
				System.out.println("You don't have a savings account and cannot transfer funds. Returning to main menu");
				userMenu();
			}
			else {
				savings_id = principal.getSavings_id();
				System.out.println("To which account would you like to make a transfer?" +
						"\n1.) Checking" +
						"\n2.) Savings" +
						"\n3.) Back");
				
				int selection = sc.nextInt();
				sc.nextLine();
				
				if(selection == 1) {
					toAcct = ToAcct.CHECKING;
					accountBalance = (savingsManager.getAccountByUserId(user_id)).getAmount();
				}
				else if(selection == 2) {
					toAcct = ToAcct.SAVINGS;
					accountBalance = (checkingManager.getAccountByUserId(user_id)).getAmount();
				}
				else if(selection == 3) {
					userMenu();
				}
				else {
					System.out.println("You must either select 1, 2 or 3.");
				}
				
				System.out.println("How much do you want to transfer?");
				double amount = sc.nextDouble();
				
				if(amount > accountBalance) {
					System.out.println("You don't have enough funds to transfer that amount.");
					userMenu();
				}
				else if(amount <= 0) {
					System.out.println("Amount must be greater than 0");
					transferFunds();
				}
				else {
					Transaction tran = new Transaction(0, LocalDateTime.now(), type, toAcct, amount, user_id, checking_id, savings_id);
					transactionManager.createTransaction(tran);
					System.out.println("Transfer initiated!");
				}
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Input invalid. Please try again");
			sc.next();
			transferFunds();
		} catch (AccountNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static void viewTransactions() {
		try {
			int user_id = principal.getId();
			int counter = 1;
			
			List<Transaction> transactionList = transactionManager.findTransactionsByUserId(user_id);
			
			Collections.reverse(transactionList);
			
			Iterator<Transaction> i = transactionList.iterator();
			
			while(counter <= 5 && i.hasNext()) {
				
				System.out.println(i.next());
				
				counter ++;
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Input invalid. Please try again.");
			sc.next();
			viewTransactions();
		}
	}
	
	public static void showInfo() {
		try {
			
			String userInfo = principal.toString();
			
			System.out.println(userInfo);
			
			double checkingBalance = checkingManager.getAccountByUserId(principal.getId()).getAmount();
			
			System.out.println("\nChecking Balance: $" + checkingBalance);
			
			if(principal.getHas_savings() == true) {
			
				double savingsBalance = savingsManager.getAccountByUserId(principal.getId()).getAmount();
			
				System.out.println("\nSavings Balance: $" + savingsBalance);
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Account not found somehow...");
			sc.next();
		} catch (AccountNotFoundException e) {
			e.getMessage();
		}
	}
	
	
}
