package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Repair Shop";
    private static final int DB_VERSION = 1;

    //Vehicle Table
    private static final String TABLE_VEHICLE = "Vehicle";
    private static final String COL_VEHICLE_ID = "vid";
    private static final String COL_VEHICLE_YEAR = "year";
    private static final String COL_VEHICLE_MAKE_MODEL = "make_model";
    private static final String COL_VEHICLE_PURCHASE_PRICE = "purchase_price";
    private static final String COL_VEHICLE_IS_NEW = "is_new";

    //Repair Table
    private static final String TABLE_REPAIR = "Repair";
    private static final String COL_REPAIR_ID = "rid";
    private static final String COL_REPAIR_VEHICLE_ID = "vehicle_vid";
    private static final String COL_REPAIR_DATE = "repair_date";
    private static final String COL_REPAIR_COST = "repair_cost";
    private static final String COL_REPAIR_DESCRIPTION = "repair_description";

    public static DBHelper getInstance(Context context){
        if(myInstance == null){
           myInstance = new DBHelper(context);
        }
        return myInstance;
    }

    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Vehicle Table
        String createVehicleTable = new StringBuilder()
                .append("CREATE TABLE ").append(TABLE_VEHICLE).append(" (")
                .append(COL_VEHICLE_ID).append(" INTEGER,")
                .append(COL_VEHICLE_YEAR).append(" INTEGER NOT NULL,")
                .append(COL_VEHICLE_MAKE_MODEL).append(" TEXT NOT NULL,")
                .append(COL_VEHICLE_PURCHASE_PRICE).append(" REAL NOT NULL,")
                .append(COL_VEHICLE_IS_NEW).append(" INTEGER NOT NULL,")
                .append("PRIMARY KEY").append("(")
                .append(COL_VEHICLE_ID).append(" AUTOINCREMENT)").append(")")
                .toString();
        db.execSQL(createVehicleTable);

        //Create Repair Table
        String createRepairTable = new StringBuilder()
                .append("CREATE TABLE ").append(TABLE_REPAIR).append(" (")
                .append(COL_REPAIR_ID).append(" INTEGER,")
                .append(COL_REPAIR_VEHICLE_ID).append(" INTEGER NOT NULL,")
                .append(COL_REPAIR_DATE).append(" TEXT NOT NULL,")
                .append(COL_REPAIR_COST).append(" REAL NOT NULL,")
                .append(COL_REPAIR_DESCRIPTION).append(" TEXT NOT NULL,")
                .append("PRIMARY KEY").append("(")
                .append(COL_REPAIR_ID).append(" AUTOINCREMENT)").append(")")
                .toString();
        db.execSQL(createRepairTable);
        Log.d("DB", "Created");
    }

    public long addVehicle(Vehicle addedVehicle){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_VEHICLE_YEAR, addedVehicle.getMakeYear());
        cv.put(COL_VEHICLE_MAKE_MODEL, addedVehicle.getModel());
        cv.put(COL_VEHICLE_PURCHASE_PRICE, addedVehicle.getPrice());
        cv.put(COL_VEHICLE_IS_NEW, addedVehicle.getIsNew() ? 1 : 0);

        long result = db.insert(TABLE_VEHICLE, null, cv);
        db.close();
        return result;
    }

    public long addRepair(Repair addedRepair){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_REPAIR_VEHICLE_ID, addedRepair.getVehicle_id());
        cv.put(COL_REPAIR_DATE, addedRepair.getRepair_date());
        cv.put(COL_REPAIR_COST, addedRepair.getRepair_cost());
        cv.put(COL_REPAIR_DESCRIPTION, addedRepair.getRepair_description());

        long result = db.insert(TABLE_REPAIR, null, cv);
        db.close();
        return result;
    }

    public List<RepairWithVehicle> getRepairsWithVehicle(String descriptionToSearch){
        List<RepairWithVehicle> repairWithVehicles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_REPAIR).append(" INNER JOIN ").append(TABLE_VEHICLE)
                .append(" ON ").append(COL_REPAIR_VEHICLE_ID).append(" = ").append(COL_VEHICLE_ID)
                .append(" WHERE ").append(COL_REPAIR_DESCRIPTION).append(" LIKE ")
                .append(String.format("'%%%s%%';", descriptionToSearch))
                .toString();

        Cursor cs = db.rawQuery(sql, null);
        if(cs.moveToFirst()){
            do{
                //Retrieve Repair information from Joined table
                int rid = cs.getInt(cs.getColumnIndexOrThrow(COL_REPAIR_ID));
                int vehicle_vid = cs.getInt(cs.getColumnIndexOrThrow(COL_REPAIR_ID));
                String repair_date = cs.getString(cs.getColumnIndexOrThrow(COL_REPAIR_DATE));
                double repair_cost = cs.getDouble(cs.getColumnIndexOrThrow(COL_REPAIR_COST));
                String repair_description = cs.getString(cs.getColumnIndexOrThrow(COL_REPAIR_DESCRIPTION));
                Repair r = new Repair(rid, vehicle_vid, repair_date, repair_cost, repair_description);

                //Retrieve Vehicle information from Joined table
                int vid = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_ID));
                int year = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_YEAR));
                String model = cs.getString(cs.getColumnIndexOrThrow(COL_VEHICLE_MAKE_MODEL));
                double price = cs.getDouble(cs.getColumnIndexOrThrow(COL_VEHICLE_PURCHASE_PRICE));
                boolean isNew = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_IS_NEW)) == 1;
                Vehicle v = new Vehicle(vid, year, model, price, isNew);

                RepairWithVehicle record = new RepairWithVehicle(v, r);
                repairWithVehicles.add(record);
            }while(cs.moveToNext());
        }
        db.close();
        return repairWithVehicles;
    }

    public List<Vehicle> getAllVehicle(){
        List<Vehicle> vehicles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s;", TABLE_VEHICLE);
        Cursor cs = db.rawQuery(sql, null);
        if(cs.moveToFirst()){
            do{
                int vid = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_ID));
                int year = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_YEAR));
                String model = cs.getString(cs.getColumnIndexOrThrow(COL_VEHICLE_MAKE_MODEL));
                double price = cs.getDouble(cs.getColumnIndexOrThrow(COL_VEHICLE_PURCHASE_PRICE));
                boolean isNew = cs.getInt(cs.getColumnIndexOrThrow(COL_VEHICLE_IS_NEW)) == 1;

                Vehicle retrievedVehicle = new Vehicle(vid, year, model, price, isNew);
                vehicles.add(retrievedVehicle);
            }while (cs.moveToNext());
        }
        db.close();
        return vehicles;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    private static DBHelper myInstance;
}
