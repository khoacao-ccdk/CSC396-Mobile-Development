package com.depauw.repairshop.database;

public class RepairWithVehicle {

    public RepairWithVehicle(Vehicle v, Repair r) {
        this.v = v;
        this.r = r;
    }

    public Vehicle getVehicle() {
        return v;
    }

    public Repair getRepair() {
        return r;
    }

    private Vehicle v;
    private Repair r;
}
