package com.depauw.attributetypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;

import com.depauw.attributetypes.databinding.ActivityAttributeTypesBinding;

public class AttributeTypes extends AppCompatActivity {

    private ActivityAttributeTypesBinding binding;

    public void task1(){
        int ratingStarColorInt = getResources().getColor(R.color.metro_red);
        ColorStateList ratingStarColor = ColorStateList.valueOf(ratingStarColorInt);
        binding.ratingbarView4.setProgressTintList(ratingStarColor);
    }

    public void task2(){
        String[] splitText = binding.edittextView2.getText().toString().split(" ");
        binding.toggleView7.setTextOff(splitText[0]);
        binding.toggleView7.setTextOn(splitText[1]);
    }

    public void task3(){
        binding.linearlayoutView6.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    public void task4(){
        binding.textviewView1.setAllCaps(true);
    }

    public void task5(){
        Drawable thumb = getResources().getDrawable(R.drawable.tiger);
        binding.seekbarView3.setThumb(thumb);
    }

    public void task6(){
        binding.edittextView2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
    }

    public void task7(){
        int numDp = 250;
        int numPx = (int)(numDp * getResources().getDisplayMetrics().density);
        binding.checkboxView8.setHeight(numPx);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttributeTypesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
    }
}