package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.utils.Commons;
import com.theleafapps.shopnick.utils.DotProgressBar;

public class NoNetworkActivity extends AppCompatActivity {

    ImageView refreshButton;
    DotProgressBar dot_progress_bar_no_network;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);

        refreshButton               = (ImageView) findViewById(R.id.refreshIcon);
        dot_progress_bar_no_network = (DotProgressBar) findViewById(R.id.dot_progress_bar_no_network);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NoNetworkActivity.this,SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
