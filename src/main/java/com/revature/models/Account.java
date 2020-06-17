package com.revature.models;

import java.util.Objects;

public class Account {
	private int id;
	private double balance;
	private AccountStatus status;
	private AccountType type;

	// Constructors
	public Account() {
		super();
	}

	public Account(int id, double balance, AccountStatus status, AccountType type) {
		super();
		this.id = id;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	// Method Override
	@Override
	public int hashCode() {
		return Objects.hash(balance, id, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id
				&& Objects.equals(status, other.status) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", status=" + status + ", type=" + type + "]";
	}

}
