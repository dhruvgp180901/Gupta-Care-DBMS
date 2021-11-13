package com.example.DBMS.model;

public class Patient {

    private int patientID;
    private String illness;
    private String admitStatus;
    private String progressStatus;
    private String medicalrecord;
    private String medicines;
    private String username;

    public int getPatientID() {
        return this.patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getIllness() {
        return this.illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getAdmitStatus() {
        return this.admitStatus;
    }

    public void setAdmitStatus(String admitStatus) {
        this.admitStatus = admitStatus;
    }

    public String getProgressStatus() {
        return this.progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getMedicalrecord() {
        return this.medicalrecord;
    }

    public void setMedicalrecord(String medicalrecord) {
        this.medicalrecord = medicalrecord;
    }

    public String getMedicines() {
        return this.medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
	public String toString() {
		return "Patient [patientID=" + patientID + ", illness=" + illness + ", admitStatus="
				+ admitStatus + ", progressStatus=" + progressStatus + ",medicalrecord=" + medicalrecord + ",medicines=" + medicines + 
                ",username=" + username + "]";
	}

}
