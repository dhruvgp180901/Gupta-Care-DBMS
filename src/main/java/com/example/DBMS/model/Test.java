package com.example.DBMS.model;

public class Test {

    private int testID;
    private String testName;
    private String setupDate;
    private String description;
    private int cost;
    private String photo;

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotosImagePath() {
        if (photo == null) return null;
         
        return "/test-photos/" + testID + "/" + photo;
    }

    public int getTestID() {
        return this.testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getSetupDate() {
        return this.setupDate;
    }

    public void setSetupDate(String setupDate) {
        this.setupDate = setupDate;
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
        return "Test [testID=" + testID + ", testName=" + testName + ", description=" + description + ", setupDate="
                + setupDate + ", cost=" + cost + ", photo=" + photo + "]";
    }

}
