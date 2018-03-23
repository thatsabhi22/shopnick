package com.theleafapps.pro.shopnick.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.pro.shopnick.utils.Commons;

public class PaymentSuccessActivity extends AppCompatActivity {

    TextView deducted_amount_tv;
    ImageButton continue_shopping;
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        if (!Commons.hasActiveInternetConnection(this)) {
            Intent intent1 = new Intent(this, NoNetworkActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        myProgressDialog = new MyProgressDialog(this);
        Intent intent = getIntent();
        double net_cost = intent.getDoubleExtra("net_cost", 0);

        deducted_amount_tv = (TextView) findViewById(R.id.deducted_amount_tv);
        continue_shopping = (ImageButton) findViewById(R.id.continue_shopping_payment_success_button);

        deducted_amount_tv.setText("Rs. " + net_cost);

        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, ShowcaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                MyProgressDialog.show(PaymentSuccessActivity.this, myProgressDialog, "", "");
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Tangho", "ProductDetail activity >> onRestart Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Tangho", "ProductDetail activity >> onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Tangho", "ProductDetail activity >> onPause Called");
    }

    @Override
    protected void onDestroy() {
        myProgressDialog.dismiss();
        super.onDestroy();
        Log.d("Tangho", "ProductDetail activity >> onDestroy Called");
    }
}
