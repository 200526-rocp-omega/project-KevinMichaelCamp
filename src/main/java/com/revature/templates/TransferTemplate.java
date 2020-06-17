package com.revature.templates;

import java.util.Objects;

public class TransferTemplate {
	private int sourceAccountId;
	private int targetAccountId;
	private double amount;

	public TransferTemplate() {
		super();
	}

	public TransferTemplate(int sourceAccountId, int targetAccountId, double amount) {
		super();
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.amount = amount;
	}

	public int getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(int sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public int getTargetAccountId() {
		return targetAccountId;
	}

	public void setTargetAccountId(int targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, sourceAccountId, targetAccountId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransferTemplate)) {
			return false;
		}
		TransferTemplate other = (TransferTemplate) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& sourceAccountId == other.sourceAccountId && targetAccountId == other.targetAccountId;
	}

	@Override
	public String toString() {
		return "TransferTemplate [sourceAccountId=" + sourceAccountId + ", targetAccountId=" + targetAccountId
				+ ", amount=" + amount + "]";
	}

}
