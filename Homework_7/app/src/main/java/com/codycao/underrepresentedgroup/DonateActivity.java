package com.codycao.underrepresentedgroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codycao.underrepresentedgroup.databinding.ActivityDonateBinding;

public class DonateActivity extends AppCompatActivity {

    private ActivityDonateBinding binding;

    public static final String EXTRA_AMOUNT = "com.codycao.underrepresentedgroup.EXTRA_AMOUNT";
    public static final String EXTRA_CREDIT_CARD = "com.codycao.underrepresentedgroup.EXTRA_CREDIT_CARD";
    public static final String EXTRA_CVC = "com.codycao.underrepresentedgroup.EXTRA_CVC";
    public static final String EXTRA_FULL_NAME = "com.codycao.underrepresentedgroup.EXTRA_FULL_NAME";
    public static final String EXTRA_PHONE = "com.codycao.underrepresentedgroup.EXTRA_PHONE";
    public static final String EXTRA_RECEIVE_RECEIPT = "com.codycao.underrepresentedgroup.EXTRA_RECEIVE_RECEIPT";

    //Strings that represent the error message
    private static final String ERROR_INVALID_AMOUNT = "Please enter a donate amount";
    private static final String ERROR_INVALID_CVC = "Please enter a valid CVC number";
    private static final String ERROR_INVALID_CARD = "Please insert a valid card number";
    private static final String ERROR_INVALID_NAME = "Please enter your full name";
    private static final String ERROR_INVALID_PHONE = "Please enter a 10-digit phone number";

    //Listeners
    private View.OnClickListener button_submit_donate_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("Phone", binding.textViewFullName.getText().toString());
            if(binding.editTextDonorName.getText().toString().length() == 0){
                showInvalidCardDialog(ERROR_INVALID_NAME);
            }
            else if(binding.editTextDonorPhone.getText().toString().length() < 10){
                showInvalidCardDialog(ERROR_INVALID_PHONE);
            }
            else if(!binding.editTextCreditCard.isCardValid()) {
                showInvalidCardDialog(ERROR_INVALID_CARD);
            }
            else if(binding.editTextCvc.getText().toString().length() < 3){
                showInvalidCardDialog(ERROR_INVALID_CVC);
            }
            else if(binding.editTextDonateAmount.getText().toString().length() == 0){
                showInvalidCardDialog(ERROR_INVALID_AMOUNT);
            }
            else{
                Intent returnIntent = new Intent();
                //Put extra information
                returnIntent.putExtra(EXTRA_FULL_NAME, binding.editTextDonorName.getText().toString());
                returnIntent.putExtra(EXTRA_PHONE, binding.editTextDonorPhone.getText().toString());
                returnIntent.putExtra(EXTRA_CREDIT_CARD, binding.editTextCreditCard.getText().toString());
                returnIntent.putExtra(EXTRA_CVC, Integer.valueOf(binding.editTextCvc.getText().toString()));
                returnIntent.putExtra(EXTRA_AMOUNT, Double.valueOf(binding.editTextDonateAmount.getText().toString()));
                returnIntent.putExtra(EXTRA_RECEIVE_RECEIPT, binding.switchReceiveReceipt.isChecked());

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    };

    private View.OnFocusChangeListener editText_credit_card_onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean b) {
            if (b == false && !binding.editTextCreditCard.isCardValid())
                showInvalidCardDialog(ERROR_INVALID_CARD);
        }
    };

    //Helper function
    private void showInvalidCardDialog(String e){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DonateActivity.this);
        alertBuilder.setTitle("Invalid Information")
                .setMessage(e)
                .setPositiveButton("OK", null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent myIntent = getIntent();
        binding.buttonSubmitDonate.setOnClickListener(button_submit_donate_onClickListener);
        binding.editTextCreditCard.setOnFocusChangeListener(editText_credit_card_onFocusChangeListener);
    }
}