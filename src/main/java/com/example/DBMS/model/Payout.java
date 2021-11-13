package com.example.DBMS.model;

public class Payout {
	
	private int payoutID;
	private String username;
	private String month;
	private int year;
	private int leavesAllowed;
	private int leavesTaken;
	private int overdaysWorked;
	private int netAmount;
	private int status;

	public int getPayoutID() {
		return this.payoutID;
	}

	public void setPayoutID(int payoutID) {
		this.payoutID = payoutID;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getLeavesAllowed() {
		return this.leavesAllowed;
	}

	public void setLeavesAllowed(int leavesAllowed) {
		this.leavesAllowed = leavesAllowed;
	}

	public int getLeavesTaken() {
		return this.leavesTaken;
	}

	public void setLeavesTaken(int leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	public int getOverdaysWorked() {
		return this.overdaysWorked;
	}

	public void setOverdaysWorked(int overdaysWorked) {
		this.overdaysWorked = overdaysWorked;
	}

	public int getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(int netAmount) {
		this.netAmount = netAmount;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Payout [payoutid=" + payoutID + ", month=" + month + ", year=" + year + ", leavesAllowed=" + leavesAllowed
				+ ", leavesTaken=" + leavesTaken + ", overdaysWorked=" + overdaysWorked + ", username=" + username +
				", netAmount=" + netAmount + ", status=" + status + "]";
	}


}
