package com.dollarsbank.model;

public class Savings extends Account{

	private static final long serialVersionUID = 2730000114751302015L;

	private int id;
	
	private double amount;
	
	private int user_id;
	
	private int checking_id;
	
	public Savings(int id, double amount, int user_id, int checking_id) {
		super(id, amount, user_id);
		this.id = id;
		this.amount = amount;
		this.user_id = user_id;
		this.checking_id = checking_id;
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

	public int getChecking_id() {
		return checking_id;
	}

	public void setChecking_id(int checking_id) {
		this.checking_id = checking_id;
	}

	@Override
	public String toString() {
		return "Savings [id=" + id + ", amount=" + amount + ", user_id=" + user_id + ", checking_id=" + checking_id
				+ "]";
	}

}
