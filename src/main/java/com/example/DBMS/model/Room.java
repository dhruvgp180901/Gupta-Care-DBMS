package com.example.DBMS.model;

public class Room {
    
    private int roomID;
    private String ward; 
    private int number;

    private String type;
    private int numberofbeds;
    private int cost;

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getWard() {
        return this.ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberofbeds() {
        return this.numberofbeds;
    }

    public void setNumberofbeds(int numberofbeds) {
        this.numberofbeds = numberofbeds;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    @Override
	public String toString() {
		return "Room [roomID=" + roomID + ", number=" + number + ", ward="
				+ ward + ", type=" + type + ",numberofbeds=" + numberofbeds + ",cost=" + cost + "]";
	}
}
