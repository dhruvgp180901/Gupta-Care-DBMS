package com.example.DBMS.model;

import java.util.Date;

public class Machine {

    private int machineID;
    private int wardID;
    private int roomID;
    private String name;
    private String purpose;
    private String color;
    private String description;
    private Date deliveredDate;

    public int getMachineID() {
        return this.machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
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

    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
    public int getWardID() {
        return this.wardID;
    }

    public void setWardID(int wardID) {
        this.wardID = wardID;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
	public String toString() {
		return "Machine [machineID=" + machineID + ", name=" + name + ", purpose="
				+ purpose + ", color=" + color + ", description=" + description + ", roomID=" + roomID + ", wardID=" + wardID +
                 ", deliveredDate=" + deliveredDate + "]";
	}
}
