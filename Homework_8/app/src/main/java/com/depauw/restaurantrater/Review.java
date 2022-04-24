package com.depauw.restaurantrater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {

    public Review(String restaurantName, Date reviewDate, String reviewTime, String meal, int rating, boolean isFavorite) {
        this.restaurantName = restaurantName;
        this.reviewDate = reviewDate;
        this.reviewTime = reviewTime;
        this.meal = meal;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    //Return a Review object from a review-formatted String
    public static Review reviewFromString(String s){
        try {
            String[] line = s.split(",");
            String restaurantName = line[0];
            SimpleDateFormat formatter = new SimpleDateFormat("m/d/yyyy");
            Date date = formatter.parse(line[1]);
            String time = line[2];
            String meal = line[3];
            int rating = Integer.valueOf(line[4]);
            boolean isFavorite = line[5].equals("1");
            Review review = new Review(restaurantName, date, time, meal, rating, isFavorite);
            return review;
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public String getMeal() {
        return meal;
    }

    public int getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    private String restaurantName;
    private Date reviewDate;
    private String reviewTime;
    private String meal;
    private int rating;
    private boolean isFavorite;
}
