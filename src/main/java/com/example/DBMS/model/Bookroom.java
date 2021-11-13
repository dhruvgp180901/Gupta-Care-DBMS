package com.example.DBMS.model;

public class Bookroom {

    private int bookroomID;
    private String username;
    private int roomID;
    private String currDate;
    private String startDate;
    private String endDate;
    private String description;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookroomID() {
        return this.bookroomID;
    }

    public void setBookroomID(int bookroomID) {
        this.bookroomID = bookroomID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrDate() {
        return this.currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
	public String toString() {
		return "Bookroom [bookroomID=" + bookroomID + ", roomID=" + roomID + ", username="
				+ username + ", currDate=" + currDate + ", startDate=" + startDate + ", endDate=" + endDate + ",description=" + description + ",status=" + status + "]";
	}
}
