package com.example.DBMS.model;

import java.util.Date;

public class Work {
	
	private int workID;
	private String hospital;
	private String workJoiningDate;
	private String username;
	private String workRole;
	private String workLeavingDate;
	private String workDescription;

	public int getWorkID() {
		return workID;
	}
	public String getHospital() {
		return hospital;
	}
	public String getWorkJoiningDate() {
		return workJoiningDate;
	}
	public String getUsername() {
		return username;
	}
	public String getWorkRole() {
		return workRole;
	}
	public String getWorkLeavingDate() {
		return workLeavingDate;
	}
	public String getWorkDescription() {
		return workDescription;
	}
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public void setWorkJoiningDate(String workJoiningDate) {
		this.workJoiningDate = workJoiningDate;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}
	public void setWorkLeavingDate(String workLeavingDate) {
		this.workLeavingDate = workLeavingDate;
	}
	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}
	@Override
	public String toString() {
		return "work [workID=" + workID + ", hospital=" + hospital + ", workJoiningDate=" + workJoiningDate + ", username="
				+ username + ", workRole=" + workRole + ", workLeavingDate=" + workLeavingDate + ", workDescription="
				+ workDescription + "]";
	}
	
	
	
}
