package com.example.DBMS.model;

import java.sql.Date;

public class User {

	private int userID;

	private String username;
	private String password;
	private String passwordConfirm;
	private String role;
	private String photo;
	private Date birthDate;
	private String gender;
	private String adhaarNumber;
	private String emailID;
	private String firstName;
	private String lastName;
	private String middleName;
	private String street;
	private String city;
	private String state;
	private String country;
	private String phone;


	public int getUserID() {
		return this.userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return this.passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotosImagePath() {
        if (photo == null) return null;
         
        return "/user-photos/" + username + "/" + photo;
    }

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdhaarNumber() {
		return this.adhaarNumber;
	}

	public void setAdhaarNumber(String adhaarNumber) {
		this.adhaarNumber = adhaarNumber;
	}

	public String getEmailID() {
		return this.emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "User [userID=" + userID + "username=" + username + ", password=" + password + ", passwordConfirm=" + passwordConfirm + ", birthDate="
				+ birthDate + ", gender=" + gender + ", adhaarNumber=" + adhaarNumber
				+ ", emailID=" + emailID + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", role=" + role + ", photo=" + photo + ", phone=" + phone +"]";
	}



	
	
	
	
	
}

