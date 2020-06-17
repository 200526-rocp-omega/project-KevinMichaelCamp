package com.revature.models;

import java.util.Objects;

public class UserAccount {
	private int user_id;
	private int account_id;
	private User user;
	private Account account;

	// Constructors
	public UserAccount() {
		super();
	}

	public UserAccount(int user_id, int account_id, User user, Account account) {
		super();
		this.user_id = user_id;
		this.account_id = account_id;
		this.user = user;
		this.account = account;
	}

	// Getters & Setters
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return Objects.hash(account, account_id, user, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserAccount)) {
			return false;
		}
		UserAccount other = (UserAccount) obj;
		return Objects.equals(account, other.account) && account_id == other.account_id
				&& Objects.equals(user, other.user) && user_id == other.user_id;
	}

	@Override
	public String toString() {
		return "UserAccount [user_id=" + user_id + ", account_id=" + account_id + ", user=" + user + ", account="
				+ account + "]";
	}

}
