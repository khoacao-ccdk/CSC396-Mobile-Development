package com.depauw.restaurantrater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListViewReview extends BaseAdapter {

    //Singleton design pattern
    public static ListViewReview getInstance(Context context){
        if(myInstance == null){
            myInstance = new ListViewReview(context);
        }
        return myInstance;
    }

    private ListViewReview(Context context) {
        this.context = context;
        getReviews();
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_review_row, parent, false);
        }
        Review review = reviews.get(i);

        TextView isFavorite = convertView.findViewById(R.id.textView_favorite);
        TextView restaurantName = convertView.findViewById(R.id.textView_restaurant_name);
        RadioGroup meals = convertView.findViewById(R.id.radioGroup_rating_display);
        ProgressBar rating = convertView.findViewById(R.id.progressBar_rating);

        if (review.isFavorite()) {
            isFavorite.setTextColor(context.getResources().getColor(R.color.teal_200));
        }
        else{
            isFavorite.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
        }
        restaurantName.setText(review.getRestaurantName());
        int reviewedMeal;
        switch (review.getMeal()){
            case "Breakfast": {
                reviewedMeal = R.id.radio_breakfast_display;
                break;
            }
            case "Lunch": {
                reviewedMeal = R.id.radio_lunch_display;
                break;
            }
            default: {
                reviewedMeal = R.id.radio_dinner_display;
            }
        }
        meals.check(reviewedMeal);
        rating.setProgress(review.getRating());

        return convertView;
    }

    private void getReviews() {
        File reviewsFile = new File(context.getFilesDir(), "reviews.csv");
        try (Scanner sc = new Scanner(reviewsFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                reviews.add(Review.reviewFromString(line));
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    //Add a new review to the ArrayList directly
    //Prevent having to re-read data from CSV file each time a new review is added
    public void addReview(String review){
        reviews.add(Review.reviewFromString(review));
    }



    public static ListViewReview myInstance;
    private ArrayList<Review> reviews = new ArrayList<>();
    Context context;

}
