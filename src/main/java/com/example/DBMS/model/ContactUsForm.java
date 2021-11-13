package com.example.DBMS.model;


public class ContactUsForm {

	int queryID;
	String phoneNumber;
	String emailID;
	String subject;

	String query;
	String name;
	private String date;

	String replyGiven;
	
	public int getQueryID() {
		return queryID;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public String getQuery() {
		return query;
	}
	public String getName() {
		return name;
	}
	public String getReplyGiven() {
		return replyGiven;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setQueryID(int queryID) {
		this.queryID = queryID;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setReplyGiven(String replyGiven) {
		this.replyGiven = replyGiven;
	}
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public String toString() {
		return "ContactUsForm [queryID=" + queryID + ", phoneNumber=" + phoneNumber + ", emailID=" + emailID + ", date=" + date
				+ ", query=" + query + ", name=" + name + ", subject=" + subject + ", replyGiven=" + replyGiven + "]";
	}
	
	
}
