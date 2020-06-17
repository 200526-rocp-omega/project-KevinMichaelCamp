package com.revature.templates;

import java.util.Objects;

public class TransactionTemplate {
	private int accountId;
	private double amount;

	public TransactionTemplate() {
		super();
	}

	public TransactionTemplate(int accountId, double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionTemplate)) {
			return false;
		}
		TransactionTemplate other = (TransactionTemplate) obj;
		return accountId == other.accountId && Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount);
	}

	@Override
	public String toString() {
		return "TransactionTemplate [accountId=" + accountId + ", amount=" + amount + "]";
	}

}
