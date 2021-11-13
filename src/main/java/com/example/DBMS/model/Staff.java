package com.example.DBMS.model;

public class Staff {

	private int staffID;
	private String work;
	private String username;
	
	public int getStaffID() {
		return staffID;
	}
	public String getWork() {
		return work;
	}
	public String getUsername() {
		return username;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Staff [staffID=" + staffID + ", work=" + work + ", username=" + username + "]";
	}	
	
}
