package com.example.DBMS.model;

public class Feedback {
    
    private int feedbackID;
    private String purpose;
    private int purposeID;
    private int rating;

    private String complaint;
    private String suggestion;
    private String date;

    public int getFeedbackID() {
        return this.feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
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

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComplaint() {
        return this.complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    @Override
	public String toString() {
		return "Feedback [feedbackID=" + feedbackID + ", purpose=" + purpose + ", rating=" + rating + ", purposeID="
				+ purposeID + ", date=" + date + ", complaint=" + complaint + ", suggestion=" + suggestion +"]";
	}

    

}
