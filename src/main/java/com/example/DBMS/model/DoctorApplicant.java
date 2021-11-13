package com.example.DBMS.model;

import java.util.Date;

public class DoctorApplicant {
	
	private int applicationID;
	private String applicantName;
	private String resume;
	private long applicantPhoneNumber;
	private String applicantEmailID;
	private String applicationDate;
	private String specialization;
		
	public int getApplicationID() {
		return applicationID;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public String getResume() {
		return resume;
	}
	public long getApplicantPhoneNumber() {
		return applicantPhoneNumber;
	}
	public String getApplicantEmailID() {
		return applicantEmailID;
	}

	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public void setApplicantPhoneNumber(long applicantPhoneNumber) {
		this.applicantPhoneNumber = applicantPhoneNumber;
	}
	public void setApplicantEmailID(String applicantEmailID) {
		this.applicantEmailID = applicantEmailID;
	}
	
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getResumePath() {
        if (resume == null) return null;
         
        return "/user-photos/resume/" + applicantName + "/" + resume;
    }

	@Override
	public String toString() {
		return "FacultyApplicant [applicationID=" + applicationID + ", applicantName=" + applicantName + ", resume="
				+ resume + ", applicantPhoneNumber=" + applicantPhoneNumber + ", applicantEmailID="
				+ applicantEmailID + ", applicationDate=" + applicationDate + ", specialization=" + specialization + "]";
	}
	
	
	
	
	
}
