package com.dollarsbank.model;

public class Checking extends Account{
	
	private int id;
	
	private double amount;
	
	private double init_deposit;
	
	private int user_id;
	
	private int savings_id;
	
	public Checking(int id, double amount, double init_deposit, int user_id, int savings_id) {
		super(id, amount, user_id);
		this.id = id;
		this.amount = amount;
		this.init_deposit = init_deposit;
		this.user_id = user_id;
		this.savings_id = savings_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInit_deposit() {
		return init_deposit;
	}

	public void setInit_deposit(double init_deposit) {
		this.init_deposit = init_deposit;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSavings_id() {
		return savings_id;
	}

	public void setSavings_id(int savings_id) {
		this.savings_id = savings_id;
	}

	@Override
	public String toString() {
		return "Checking [id=" + id + ", amount=" + amount + ", init_deposit=" + init_deposit + ", user_id=" + user_id
				+ ", savings_id=" + savings_id + "]";
	}

}
