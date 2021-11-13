package com.example.DBMS.model;

import java.sql.Date;

public class Medicine {

    private int medicineID;
    private String name;
    private String purpose;
    private String company;
    private String photo;

    private String color;
    private String description;
    private int cost;
    private String deliveredAmount;
    private Date deliveredDate;

    public int getMedicineID() {
        return this.medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getDeliveredAmount() {
        return this.deliveredAmount;
    }

    public void setDeliveredAmount(String deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotosImagePath() {
        if (photo == null) return null;
         
        return "/medicine-photos/" + medicineID + "/" + photo;
    }

    @Override
    public String toString() {
        return "Medicine [medicineID=" + medicineID + ", name=" + name + ", purpose=" + purpose + ", color=" + color
                + ", description=" + description + ", cost=" + cost + ", photo=" + photo + ", company=" + company
                + ", deliveredAmount=" + deliveredAmount + ", deliveredDate=" + deliveredDate + "]";
    }
}
