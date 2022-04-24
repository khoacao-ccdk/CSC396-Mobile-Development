package com.depauw.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.depauw.restaurantrater.databinding.ActivityAddReviewBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AddReviewActivity extends AppCompatActivity {

    private ActivityAddReviewBinding binding;

    public static final String EXTRA_REVIEW_STRING = "com.depauw.restaurantrater.EXTRA_RESTAURANT_NAME";

    //Listeners
    View.OnClickListener button_add_review_clickListener = view -> {
        File reviewsFile = new File(getFilesDir(), "reviews.csv");
        try (FileWriter myWriter = new FileWriter(reviewsFile, true)) {
            String restaurantName = binding.edittextRestaurantName.getText().toString();
            String reviewDate = binding.edittextReviewDate.getText().toString();
            String reviewTime = binding.edittextReviewTime.getText().toString();
            String meal;
            switch(binding.radiogroupMeals.getCheckedRadioButtonId()){
                case R.id.radio_breakfast:
                    meal = "Breakfast";
                    break;
                case R.id.radio_lunch:
                    meal = "Lunch";
                    break;
                default:
                    meal = "Dinner";
                    break;
            };
            int rating = binding.seekbarRating.getProgress();
            String isFavorite = binding.checkboxFavorite.isChecked() ? "1" : "0";

            //Make sure restaurant name, review date, and review time is filled before adding new review to csv file
            if(restaurantName.equals("") || reviewDate.equals("") || reviewTime.equals(""))
                Toast.makeText(AddReviewActivity.this, "Incorrect/Empty Information", Toast.LENGTH_SHORT).show();
            else {
                String newReview = new StringBuilder()
                        .append(restaurantName).append(",")
                        .append(reviewDate).append(",")
                        .append(reviewTime).append(",")
                        .append(meal).append(",")
                        .append(rating).append(",")
                        .append(isFavorite)
                        .toString();
                myWriter.write(newReview + System.lineSeparator());
                myWriter.close();

                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_REVIEW_STRING, newReview);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    View.OnClickListener edittext_review_date_clickListener = view -> {
        DatePickerDialog picker = new DatePickerDialog(AddReviewActivity.this);

        picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                binding.edittextReviewDate.setText(1 + month + "/" + dateOfMonth + "/" + year);
            }
        });

        picker.show();
    };

    View.OnClickListener edittext_review_time_clickListener = view -> {
        TimePickerDialog picker = new TimePickerDialog(AddReviewActivity.this, (timePicker, hourOfDay, minute) -> {
            String setTime = new StringBuilder()
                    .append(hourOfDay <= 12 ? hourOfDay : hourOfDay - 12)
                    .append(":")
                    .append(minute < 10 ? "0" + minute : minute)
                    .append(hourOfDay <= 12 ? " AM" : " PM")
                    .toString();
            binding.edittextReviewTime.setText(setTime);
        }, LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), false);

        picker.show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);
        binding.edittextReviewDate.setOnClickListener(edittext_review_date_clickListener);
        binding.edittextReviewTime.setOnClickListener(edittext_review_time_clickListener);
    }
}