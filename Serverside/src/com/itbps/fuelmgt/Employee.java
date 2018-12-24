package com.itbps.fuelmgt;

import java.util.Date;

public class Employee {
	private String name;
	private String firstName;
	private String lastName;
	private Date dateofhire;
	private String ssn;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateofhire() {
		return dateofhire;
	}

	public void setDateofhire(Date dateofhire) {
		this.dateofhire = dateofhire;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}
