package com.revature.templates;

import java.util.Objects;

import com.revature.models.Account;

public class AccountTemplate {
	private int user_id;
	private Account account;

	// Constructors
	public AccountTemplate() {
		super();
	}

	public AccountTemplate(int user_id, Account account) {
		super();
		this.user_id = user_id;
		this.account = account;
	}

	// Getters & Setters
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	// Method Override
	@Override
	public int hashCode() {
		return Objects.hash(account, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountTemplate)) {
			return false;
		}
		AccountTemplate other = (AccountTemplate) obj;
		return Objects.equals(account, other.account) && user_id == other.user_id;
	}

	@Override
	public String toString() {
		return "AccountTemplate [user_id=" + user_id + ", account=" + account + "]";
	}

}
