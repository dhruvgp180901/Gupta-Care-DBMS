package com.example.DBMS.model;

public class Order {

    private int orderID;
    private String username;
    private int medicineID;
    private String date;
    private int amount;
    private int cost;

    public int getOrderID() {
        return this.orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMedicineID() {
        return this.medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }


    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
	public String toString() {
		return "Order [orderID=" + orderID + ", username=" + username + ", medicineID="
				+ medicineID + ", date=" + date + ", amount=" + amount + ", cost=" + cost + "]";
	}
    
}
