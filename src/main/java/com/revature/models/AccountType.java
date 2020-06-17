package com.revature.models;

import java.util.Objects;

public class AccountType {
	public int id;
	public String type;

	// Constructors
	public AccountType() {
		super();
	}

	public AccountType(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// Method Override
	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountType)) {
			return false;
		}
		AccountType other = (AccountType) obj;
		return id == other.id && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "AccountType [id=" + id + ", type=" + type + "]";
	}

}
