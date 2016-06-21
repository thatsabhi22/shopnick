package com.theleafapps.shopnick.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.theleafapps.shopnick.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("qwe","eweq");

        Toast.makeText(this,"Main Activity Run",Toast.LENGTH_LONG).show();
    }
}