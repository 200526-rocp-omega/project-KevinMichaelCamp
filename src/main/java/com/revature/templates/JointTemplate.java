package com.revature.templates;

import java.util.Objects;

public class JointTemplate {
	private int accountId;
	private int userId;

	public JointTemplate() {
		super();
	}

	public JointTemplate(int accountId, int userId) {
		super();
		this.accountId = accountId;
		this.userId = userId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof JointTemplate)) {
			return false;
		}
		JointTemplate other = (JointTemplate) obj;
		return accountId == other.accountId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "JointTemplate [accountId=" + accountId + ", userId=" + userId + "]";
	}

}
