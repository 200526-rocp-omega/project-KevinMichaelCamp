package com.revature.templates;

import java.util.Objects;

public class TimeTemplate {
	private int numOfMonths;

	public TimeTemplate() {
		super();
	}

	public TimeTemplate(int numOfMonths) {
		super();
		this.numOfMonths = numOfMonths;
	}

	public int getNumOfMonths() {
		return numOfMonths;
	}

	public void setNumOfMonths(int numOfMonths) {
		this.numOfMonths = numOfMonths;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numOfMonths);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TimeTemplate)) {
			return false;
		}
		TimeTemplate other = (TimeTemplate) obj;
		return numOfMonths == other.numOfMonths;
	}

	@Override
	public String toString() {
		return "TimeTemplate [numOfMonths=" + numOfMonths + "]";
	}

}
