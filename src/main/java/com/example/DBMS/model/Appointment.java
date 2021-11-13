package com.example.DBMS.model;

public class Appointment {

	private int appointmentID;
	private String patientName;
	private String doctorName;

	private String currDate;

	private String bookDate;
	private String bookStime;
	private String bookEtime;

	private String description;
	private String status;


	public int getAppointmentID() {
		return this.appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getCurrDate() {
		return this.currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public String getBookDate() {
		return this.bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBookStime() {
		return this.bookStime;
	}

	public void setBookStime(String bookStime) {
		this.bookStime = bookStime;
	}

	public String getBookEtime() {
		return this.bookEtime;
	}

	public void setBookEtime(String bookEtime) {
		this.bookEtime = bookEtime;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Appointment [appointmentID=" + appointmentID + "patientName=" + patientName + "doctorName=" + doctorName + ", currDate=" + currDate + ", bookDate=" + bookDate + ", bookStime=" + bookStime
				+ ", bookEtime=" + bookEtime + ", description=" + description + ", status=" + status +  "]";
	}
	
	

}