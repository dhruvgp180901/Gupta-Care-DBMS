package com.example.DBMS.model;

public class PayorderMed {

    private int payorderMedID;
    private int paymentID;
    private int medicineID;
    private int quantity;

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPayorderMedID() {
        return this.payorderMedID;
    }

    public void setPayorderMedID(int payorderMedID) {
        this.payorderMedID = payorderMedID;
    }

    public int getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getMedicineID() {
        return this.medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }
    
    @Override
	public String toString() {
		return "PayorderMed [PayorderMedID=" + payorderMedID + ", paymentID=" + paymentID + ", quantity=" + quantity + ", medicineID="
				+ medicineID + "]";
	}
}
