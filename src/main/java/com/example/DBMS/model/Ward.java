package com.example.DBMS.model;

public class Ward {
    
    private int wardID;
    private String name;
    private int number;
    private String type;
    private int numberofrooms;

    public int getWardID() {
        return this.wardID;
    }

    public void setWardID(int wardID) {
        this.wardID = wardID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNumberofrooms() {
        return this.numberofrooms;
    }

    public void setNumberofbeds(int numberofrooms) {
        this.numberofrooms = numberofrooms;
    }

    @Override
	public String toString() {
		return "Ward [wardID=" + wardID + ", name=" + name + ", number="
				+ number + ", type=" + type + ",numberofrooms=" + numberofrooms + "]";
	}
}
