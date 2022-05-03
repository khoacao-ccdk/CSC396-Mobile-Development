package com.depauw.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;

public class AddVehicleActivity extends AppCompatActivity {

    private ActivityAddVehicleBinding binding;

    private View.OnClickListener button_add_vehicle_clickListener = (View v) -> {
        String year = binding.edittextYear.getText().toString();
        String model = binding.edittextMakeModel.getText().toString();
        String price = binding.edittextPrice.getText().toString();
        boolean isNew = binding.checkboxIsNew.isChecked();
        if(year.length() != 4 || model.equals("") || price.equals("")){
            AlertDialog alert = new AlertDialog.Builder(AddVehicleActivity.this)
                    .setTitle("Insufficient Information")
                    .setMessage("Please fill in every field.")
                    .setPositiveButton("OK", null)
                    .create();
            alert.show();
        }
        else {
            Vehicle addedVehicle = new Vehicle(Integer.valueOf(year), model, Double.valueOf(price), isNew);

            DBHelper helper = DBHelper.getInstance(AddVehicleActivity.this);
            long addResult = helper.addVehicle(addedVehicle);
            if (addResult != -1) {
                Toast toast = Toast.makeText(AddVehicleActivity.this, "Vehicle added successfully!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            } else {
                Toast toast = Toast.makeText(AddVehicleActivity.this, "An error occurred, please try again.", Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(button_add_vehicle_clickListener);
    }
}