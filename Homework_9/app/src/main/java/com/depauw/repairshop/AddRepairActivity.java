package com.depauw.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddRepairActivity extends AppCompatActivity {

    private ActivityAddRepairBinding binding;

    //Listeners
    private View.OnClickListener button_add_repair_clickListener = view -> {
        Vehicle selectedVehicle = (Vehicle) binding.spinnerVehicles.getSelectedItem();
        int vid = selectedVehicle.getVid();
        String repairDate = binding.edittextRepairDate.getText().toString();
        String repairCost = binding.edittextRepairCost.getText().toString();
        String repairDescription = binding.edittextRepairDescription.getText().toString();
        if (repairDate.equals("") || repairCost.equals("") || repairDescription.equals("")) {
            AlertDialog alert = new AlertDialog.Builder(AddRepairActivity.this)
                    .setTitle("Insufficient Information")
                    .setMessage("Please fill in every field.")
                    .setPositiveButton("OK", null)
                    .create();
            alert.show();
        }
        else {
            Repair addedRepair = new Repair(vid, repairDate, Double.valueOf(repairCost), repairDescription);
            DBHelper helper = DBHelper.getInstance(AddRepairActivity.this);
            long addResult = helper.addRepair(addedRepair);

            if (addResult != -1) {
                Toast toast = Toast.makeText(AddRepairActivity.this, "Repair added successfully!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            } else {
                Toast toast = Toast.makeText(AddRepairActivity.this, "An error occurred, please try again.", Toast.LENGTH_SHORT);
            }
        }
    };

    private View.OnClickListener edittext_repair_date_clickListener = view -> {
        DatePickerDialog dpDialog = new DatePickerDialog(this);
        dpDialog.setOnDateSetListener((datePicker, year, month, dayOfMonth) -> {
            String setDate = new StringBuilder()
                    .append(year)
                    .append("-")
                    .append(month + 1 > 9 ? month + 1 : "0" + (month + 1))
                    .append("-")
                    .append(dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth)
                    .toString();
            binding.edittextRepairDate.setText(setDate);
        });
        dpDialog.show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = DBHelper.getInstance(this);
        List<Vehicle> vehicles = helper.getAllVehicle();
        ArrayAdapter<Vehicle> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicles);
        binding.spinnerVehicles.setAdapter(myAdapter);

        binding.buttonAddRepair.setOnClickListener(button_add_repair_clickListener);
        binding.edittextRepairDate.setOnClickListener(edittext_repair_date_clickListener);
    }
}