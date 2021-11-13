package com.example.DBMS.model;

import java.sql.Date;

public class Testbooking {

    private int testbookingID;
    private String testName;
    private String patientName;
    private String currDate;
    private Date bookDate;
    private String bookTime;
    private String diseaseDescription;
    private String status;

    public int getTestbookingID() {
        return this.testbookingID;
    }

    public void setTestbookingID(int testbookingID) {
        this.testbookingID = testbookingID;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCurrDate() {
        return this.currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public Date getBookDate() {
        return this.bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookTime() {
        return this.bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getDiseaseDescription() {
        return this.diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
	public String toString() {
		return "Testbooking [testbookingID=" + testbookingID + ", testName=" + testName + ", patientName="
				+ patientName + ", currDate=" + currDate + ", bookDate=" + bookDate + ", bookTime=" + bookTime + ", diseaseDescription=" + 
                diseaseDescription + ", status=" + status + "]";
	}
}
