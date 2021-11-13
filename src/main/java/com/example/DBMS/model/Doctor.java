package com.example.DBMS.model;

public class Doctor {
	
	private int doctorID;
	private String parentName;
	private String parentOccupation;
	private String collegePGrad;
	private int percentagePGrad;
	private String collegeGrad;
	private int percentageGrad;
	private String board12th;
	private int percentage12th;
	private String board10th;
	private int percentage10th;
	private String specialization;
	private String designation;
	private String username;
	private int appointmentCost;
	private int salary;
	
	public int getDoctorID() {
		return doctorID;
	}
	public String getParentName() {
		return parentName;
	}
	public String getParentOccupation() {
		return parentOccupation;
	}
	public String getCollegePGrad() {
		return collegePGrad;
	}
	public int getPercentagePGrad() {
		return percentagePGrad;
	}
	public String getCollegeGrad() {
		return collegeGrad;
	}
	public int getPercentageGrad() {
		return percentageGrad;
	}
	public String getBoard12th() {
		return board12th;
	}
	public int getPercentage12th() {
		return percentage12th;
	}
	public String getBoard10th() {
		return board10th;
	}
	public int getPercentage10th() {
		return percentage10th;
	}
	public String getSpecialization() {
		return specialization;
	}
	public String getDesignation() {
		return designation;
	}
	
	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public void setParentOccupation(String parentOccupation) {
		this.parentOccupation = parentOccupation;
	}
	public void setCollegePGrad(String collegePGrad) {
		this.collegePGrad = collegePGrad;
	}
	public void setPercentagePGrad(int percentagePGrad) {
		this.percentagePGrad = percentagePGrad;
	}
	public void setCollegeGrad(String collegeGrad) {
		this.collegeGrad = collegeGrad;
	}
	public void setPercentageGrad(int percentageGrad) {
		this.percentageGrad = percentageGrad;
	}
	public void setBoard12th(String board12th) {
		this.board12th = board12th;
	}
	public void setPercentage12th(int percentage12th) {
		this.percentage12th = percentage12th;
	}
	public void setBoard10th(String board10th) {
		this.board10th = board10th;
	}
	public void setPercentage10th(int percentage10th) {
		this.percentage10th = percentage10th;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAppointmentCost() {
		return this.appointmentCost;
	}

	public void setAppointmentCost(int appointmentCost) {
		this.appointmentCost = appointmentCost;
	}

	public int getSalary() {
		return this.salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "Doctor [doctorID=" + doctorID +  ", parentName=" + parentName
				+ ", parentOccupation=" + parentOccupation + ", collegePGrad="
				+ collegePGrad + ", percentagePGrad=" + percentagePGrad + ", collegeGrad=" + collegeGrad
				+ ", percentageGrad=" + percentageGrad + ", board12th=" + board12th + ", percentage12th="
				+ percentage12th + ", board10th=" + board10th + ", percentage10th=" + percentage10th + ", specialization="
				+ specialization + ", designation=" + designation + ", username=" + username + ", appointmentCost=" + appointmentCost + ", salary=" + salary +"]";
	}
	
	
	

		
}
