package com.example.DBMS.model;

public class Bill {

    private int billID;
    private String patientName;
    private String doctorName;
    private String date;
    private String time;
    private String description;

    private int cost;

    public int getBillID() {
        return this.billID;
    }

    public void setBillId(int billID) {
        this.billID = billID;
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
	public String toString() {
		return "Bill [billID=" + billID + "patientName=" + patientName + "doctorName=" + doctorName + ", date=" + date + ", time=" + time
				+ ", description=" + description + "cost=" + cost + "]";
	}
}
