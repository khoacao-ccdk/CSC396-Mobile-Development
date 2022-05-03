package com.depauw.repairshop.database;

public class Repair {

    //Constructor used for creating a new Repair record
    public Repair(int vehicle_id, String repair_date, double repair_cost, String repair_description) {
        this.vehicle_id = vehicle_id;
        this.repair_date = repair_date;
        this.repair_cost = repair_cost;
        this.repair_description = repair_description;
    }

    //Constructor used for retrieving Repair information
    public Repair(int rid, int vehicle_id, String repair_date, double repair_cost, String repair_description){
        this(vehicle_id, repair_date, repair_cost, repair_description);
        this.rid = rid;
    }

    public int getRId() {
        return rid;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getRepair_date() {
        return repair_date;
    }

    public double getRepair_cost() {
        return repair_cost;
    }

    public String getRepair_description() {
        return repair_description;
    }

    private int rid;
    private int vehicle_id;
    private String repair_date;
    private double repair_cost;
    private String repair_description;
}


