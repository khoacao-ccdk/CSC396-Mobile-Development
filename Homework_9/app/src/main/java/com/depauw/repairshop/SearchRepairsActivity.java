package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.RepairWithVehicle;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.List;

public class SearchRepairsActivity extends AppCompatActivity {

    private ActivitySearchRepairsBinding binding;

    //Listeners
    private View.OnClickListener button_find_repairs_clickListener = view -> {
        String descriptionToSearch = binding.edittextSearchPhrase.getText().toString();
        DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);
        List<RepairWithVehicle> result = helper.getRepairsWithVehicle(descriptionToSearch);

        ListViewResult myAdapter = ListViewResult.getInstance(result,SearchRepairsActivity.this);
        binding.listviewResults.setAdapter(myAdapter);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonFindRepairs.setOnClickListener(button_find_repairs_clickListener);
    }

}