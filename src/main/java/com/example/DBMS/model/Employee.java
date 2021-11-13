package com.example.DBMS.model;

import java.util.Date;

public class Employee {
	
	private int employeeID;
	private float salary;
	private long accountNumber;
	private String PAN;
	private Date joiningDate;
	private String username;

	public int getEmployeeID() {
		return employeeID;
	}
	public float getSalary() {
		return salary;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public String getPAN() {
		return PAN;
	}
	
	public Date getJoiningDate() {
		return joiningDate;
	}

	public String getUsername() {
		return username;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setPAN(String pAN) {
		PAN = pAN;
	}
	
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setUser(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", salary=" + salary + ", accountNumber="
				+ accountNumber + ", PAN=" + PAN
				+ ", joiningDate=" + joiningDate + ", username=" + username + "]";
	}
	
	
}
	