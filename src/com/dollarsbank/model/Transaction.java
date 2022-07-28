package com.dollarsbank.model;

import java.time.LocalDateTime;

import com.dollarsbank.application.Main.ToAcct;
import com.dollarsbank.application.Main.Type;



public class Transaction {
	
	private int id;
	
	private LocalDateTime date;
	
	private Type type;
	
	private ToAcct toAcct;
	
	private double amount;
	
	private int user_id;
	
	private int checking_id;
	
	private int savings_id;
	
	public Transaction(int id, LocalDateTime date, Type type, ToAcct toAcct, double amount, int user_id, int checking_id, int savings_id) {
		
		super();
		this.id = id;
		this.date = date;
		this.type = type;
		this.toAcct = toAcct;
		this.amount = amount;
		this.user_id = user_id;
		this.checking_id = checking_id;
		this.savings_id = savings_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ToAcct getToAcct() {
		return toAcct;
	}

	public void setToAcct(ToAcct toAcct) {
		this.toAcct = toAcct;
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

	public int getSavings_id() {
		return savings_id;
	}

	public void setSavings_id(int savings_id) {
		this.savings_id = savings_id;
	}

	@Override
	public String toString() {
		return "id=" + id + ", date=" + date + ", type=" + type + ", toAcct=" + toAcct + ", amount="
				+ amount + ", user_id=" + user_id + ", checking_id=" + checking_id + ", savings_id=" + savings_id;
	}

}
