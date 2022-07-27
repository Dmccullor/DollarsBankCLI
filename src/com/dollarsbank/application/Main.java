package com.dollarsbank.application;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.dollarsbank.controller.AccountManager;
import com.dollarsbank.controller.CustomerController;
import com.dollarsbank.controller.CustomerManager;
import com.dollarsbank.model.Checking;
import com.dollarsbank.model.Customer;

public class Main {

	public enum Type {
		DEPOSIT, WITHDRAWAL, TRANSFER
	}
	
	public enum ToAcct {
		CHECKING, SAVINGS
	}
	
	private static CustomerManager manager;
	private static AccountManager checkingManager;
	private static Scanner sc;
	
	public static void main(String[] args) {
		
		manager = new CustomerController();
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
					// login();
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
			System.out.println("Please enter your full name...");
			String name = sc.nextLine();
			
			System.out.println("Please enter your address...");
			String address = sc.nextLine();
			
			System.out.println("Please enter your phone number...");
			long phone = sc.nextLong();
			sc.nextLine();
			
			System.out.println("Please enter a password for your account...");
			String password = sc.nextLine();
			
			Customer newCust = new Customer(0, name, address, phone, password, 0, false, 0);
			manager.createCustomer(newCust);
			
			
			
			
		} catch(InputMismatchException e) {
			
			System.out.println("Input Invalid. Please start over and try again.");
			createCustomer();
		}
		
	}
	
	public static void createCheckingAccount() {
		
		try {
			System.out.println("\nPlease enter your new ID number to set up your checking account...");
			int user_id = sc.nextInt();
			
			System.out.println("New accounts require an initial deposit of at least $500. How much would you like to deposit?");
			double init_amount = sc.nextDouble();
			
			if(init_amount < 500.00) {
				System.out.println("Initial deposit must be at leasst $500.00");
				createCheckingAccount();
			}
			
			Checking newChecking = new Checking(0, init_amount, init_amount, user_id, 0);
			checkingManager.createAccount(newChecking);
			
		} catch(InputMismatchException e) {
			System.out.println("Input Invalid. Please start over and try again.");
			createCheckingAccount();
		}
		
	}
}
