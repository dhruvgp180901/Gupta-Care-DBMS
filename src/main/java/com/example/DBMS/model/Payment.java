package com.example.DBMS.model;

public class Payment {

    private int paymentID;
    private String purpose;
    private int purposeID;
    private int amount;
    private String payDate;
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return this.cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    private String status;

    public int getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getPurposeID() {
        return this.purposeID;
    }

    public void setPurposeID(int purposeID) {
        this.purposeID = purposeID;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayDate() {
        return this.payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
	public String toString() {
		return "Payment [paymentID=" + paymentID + ", purpose=" + purpose + ", purposeID=" + purposeID + ", amount="
				+ amount + ", status=" + status + "]";
	}
}
