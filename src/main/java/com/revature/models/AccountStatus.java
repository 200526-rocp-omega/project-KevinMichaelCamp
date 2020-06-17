package com.revature.models;

import java.util.Objects;

public class AccountStatus {
	private int id;
	private String status;

	// Constructors
	public AccountStatus() {
		super();
	}

	public AccountStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Method Override
	@Override
	public int hashCode() {
		return Objects.hash(id, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountStatus)) {
			return false;
		}
		AccountStatus other = (AccountStatus) obj;
		return id == other.id && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "AccountStatus [id=" + id + ", status=" + status + "]";
	}

}
