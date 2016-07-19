package com.theleafapps.shopnick.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.theleafapps.shopnick.R;

public class CheckoutActivity extends AppCompatActivity {

    ActionBar actionBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);

        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Complete Your Order");



    }
}
