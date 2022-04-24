package com.depauw.restaurantrater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.depauw.restaurantrater.databinding.ActivityViewReviewsBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ViewReviewsActivity extends AppCompatActivity {

    private ActivityViewReviewsBinding binding;
    private static final int FROM_ADD_REVIEW_ACTIVITY = 1;
    ListViewReview myAdapter;

    //Listeners
    private View.OnClickListener button_add_review_clickListener = view -> {
        Intent theIntent = new Intent(ViewReviewsActivity.this, AddReviewActivity.class);
        startActivityForResult(theIntent, FROM_ADD_REVIEW_ACTIVITY);
    };

    private ListView.OnItemClickListener listview_review_itemClickListener = (AdapterView<?> parent, View v, int position, long id) -> {
        Review review = (Review) myAdapter.getItem(position);
        DateFormat dateFormat = new SimpleDateFormat("m/d/yyyy");
        String date = dateFormat.format(review.getReviewDate());
        String time = review.getReviewTime();

        String message = new StringBuilder("This review was created on ")
                .append(date)
                .append(" at ")
                .append(time)
                .toString();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Review Details")
                .setMessage(message);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);

        myAdapter = ListViewReview.getInstance(this);
        binding.listviewReviews.setOnItemClickListener(listview_review_itemClickListener);
        binding.listviewReviews.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null){
            Toast.makeText(this, "Review Added Successfully", Toast.LENGTH_SHORT).show();
            myAdapter.addReview(data.getStringExtra(AddReviewActivity.EXTRA_REVIEW_STRING));
            binding.listviewReviews.setAdapter(myAdapter);
        }
    }
}