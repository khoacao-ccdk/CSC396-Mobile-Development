package com.codycao.underrepresentedgroup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.codycao.underrepresentedgroup.databinding.ActivityBiographyBinding;

public class BiographyActivity extends AppCompatActivity {

    private ActivityBiographyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBiographyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}