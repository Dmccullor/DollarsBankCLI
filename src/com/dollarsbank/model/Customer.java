package com.dollarsbank.model;

public class Customer {

	private int id;
	
	private String name;
	
	private String address;
	
	private long phone;
	
	private String password;
	
	private int checking_id;
	
	private boolean has_savings;
	
	private int savings_id;
	
	public Customer(int id, String name, String address, long phone, String password, int checking_id, boolean has_savings, int savings_id) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.checking_id = checking_id;
		this.has_savings = has_savings;
		this.savings_id = savings_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getChecking_id() {
		return checking_id;
	}

	public void setChecking_id(int checking_id) {
		this.checking_id = checking_id;
	}
	
	public boolean getHas_savings() {
		return has_savings;
	}
	
	public void setHas_savings(boolean has_savings) {
		this.has_savings = has_savings;
	}

	public int getSavings_id() {
		return savings_id;
	}

	public void setSavings_id(int savings_id) {
		this.savings_id = savings_id;
	}

	@Override
	public String toString() {
		return "ID = " + id + ", Name = " + name + ", Address = " + address + ", Phone # = " + phone + 
				", Checking ID = " + checking_id + ", Has a Savings Account = " + has_savings + ", Savings ID = "
				+ savings_id;
	}

	
	
}
