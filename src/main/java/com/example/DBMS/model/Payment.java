package com.example.DBMS.model;

public class Payment {

    private int paymentID;
    private String purpose;
    private int purposeID;
    private int amount;
    private String payDate;
    private String transactionid;

    public String getTransactionid() {
        return this.transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

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

    public String getPayDate() {
        return this.payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
	public String toString() {
		return "Payment [paymentID=" + paymentID + ", purpose=" + purpose + ", purposeID=" + purposeID + ", amount="
				+ amount + ", transactonID=" + transactionid + "]";
	}
}
