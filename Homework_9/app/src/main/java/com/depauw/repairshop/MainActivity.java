package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddRepair.setOnClickListener(this);
        binding.buttonAddVehicle.setOnClickListener(this);
        binding.buttonSearchRepairs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent theIntent;
        switch (v.getId()){
            case R.id.button_add_repair:
                theIntent = new Intent(MainActivity.this, AddRepairActivity.class);
                break;
            case R.id.button_add_vehicle:
                theIntent = new Intent(MainActivity.this, AddVehicleActivity.class);
                break;
            default:
                theIntent = new Intent(MainActivity.this, SearchRepairsActivity.class);
        }
        startActivity(theIntent);
    }
}