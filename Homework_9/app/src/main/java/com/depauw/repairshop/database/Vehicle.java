package com.depauw.repairshop.database;

public class Vehicle {

    //Constructor used for constructing a new Vehicle record
    public Vehicle(int makeYear, String model, double price, boolean isNew) {
        this.makeYear = makeYear;
        this.model = model;
        this.price = price;
        this.isNew = isNew;
    }

    //Constructor used for retrieving Vehicle information
    public Vehicle(int vid, int makeYear, String model, double price, boolean isNew) {
        this(makeYear, model, price, isNew);
        this.vid = vid;
    }

    public int getVid() {
        return vid;
    }

    public int getMakeYear() {
        return makeYear;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public boolean getIsNew() {
        return isNew;
    }

    @Override
    public String toString(){
        return makeYear + " " + model;
    }

    private int vid;
    private int makeYear;
    private String model;
    private double price;
    private boolean isNew;
}
