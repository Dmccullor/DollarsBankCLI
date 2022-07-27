package com.dollarsbank.model;

public abstract class Account {
	
	private int id;
	
	private double amount;
	
	private int user_id;
	
	public Account(int id, double amount, int user_id) {
		this.id = id;
		this.amount = amount;
		this.user_id = user_id;
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



	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	@Override
	public String toString() {
		return "Account [id=" + id + ", amount=" + amount + ", user_id=" + user_id + "]";
	}

}
