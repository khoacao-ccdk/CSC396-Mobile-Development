package com.codycao.underrepresentedgroup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codycao.underrepresentedgroup.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int FROM_DONATE_ACTIVITY = 1;

    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonBiography.setOnClickListener(this);
        binding.buttonDonate.setOnClickListener(this);
        binding.buttonMoreInfo.setOnClickListener(this);
        binding.textViewPersonPlaceOfBirth.setOnClickListener(this);
        binding.textViewPersonLocation.setOnClickListener(this);
    }

    //Listeners
    @Override
    public void onClick(View v) {
        Intent theIntent;

        switch (v.getId()){
            case R.id.button_biography:
                theIntent = new Intent(this, BiographyActivity.class);
                break;
            case R.id.button_donate:
                theIntent = new Intent(this, DonateActivity.class);
                break;
            case R.id.button_more_info:
                theIntent = new Intent(Intent.ACTION_VIEW);
                theIntent.setData(Uri.parse("https://en.wikipedia.org/wiki/Bui_Tuong_Phong"));
                break;
            case R.id.textView_person_place_of_birth:
                theIntent = new Intent(Intent.ACTION_VIEW);
                theIntent.setData(Uri.parse("geo:0,0?q=Hanoi+Vietnam"));
                break;
            case R.id.textView_person_location:
                theIntent = new Intent(Intent.ACTION_VIEW);
                theIntent.setData(Uri.parse("geo:0,0?q=1919+E+Santa+Clara+Ave,+Santa+Ana,+CA"));
                break;
            default:
                theIntent = new Intent();
        }
        if(theIntent.resolveActivity(getPackageManager()) != null) {
            if (v.getId() == R.id.button_donate)
                startActivityForResult(theIntent, FROM_DONATE_ACTIVITY);
            else
                startActivity(theIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FROM_DONATE_ACTIVITY && resultCode == Activity.RESULT_OK) {
            if(data.getBooleanExtra(DonateActivity.EXTRA_RECEIVE_RECEIPT, false)){
                Intent textIntent = new Intent(Intent.ACTION_SENDTO);
                textIntent.setData(Uri.parse("sms:" + data.getStringExtra(DonateActivity.EXTRA_PHONE)));

                //Compose message body
                String messageBody = new StringBuilder("Thank you ")
                        .append(data.getStringExtra(DonateActivity.EXTRA_FULL_NAME))
                        .append(" for your donation of $")
                        .append(data.getDoubleExtra(DonateActivity.EXTRA_AMOUNT, 0.0))
                        .append(" using card number ending in ")
                        .append(data.getStringExtra(DonateActivity.EXTRA_CREDIT_CARD).split("-")[3])
                        .toString();

                textIntent.putExtra("sms_body", messageBody);
                if(textIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(textIntent);
            }

        }
    }
}