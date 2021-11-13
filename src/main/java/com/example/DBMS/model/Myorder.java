package com.example.DBMS.model;

public class Myorder {

    private int myorderID;
    private int orderID;
    private int paymentID;

    public int getMyorderID() {
        return this.myorderID;
    }

    public void setMyorderID(int myorderID) {
        this.myorderID = myorderID;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    
    @Override
	public String toString() {
		return "Myorder [myorderID=" + myorderID + ", orderID=" + orderID + ", paymentID="
				+ paymentID + "]";
	}
    
}
