package com.theleafapps.pro.shopnick.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.models.Customer;
import com.theleafapps.pro.shopnick.models.multiples.Customers;
import com.theleafapps.pro.shopnick.tasks.AddCustomerTask;
import com.theleafapps.pro.shopnick.tasks.GetCustomerByCustomerDevIdTask;
import com.theleafapps.pro.shopnick.utils.Commons;

import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity {

    String and_id;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case app logo_small / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    try {
                        Intent intent;

                        if (Commons.hasActiveInternetConnection(SplashActivity.this)) {
                            processCustomer();
                            intent = new Intent(SplashActivity.this, ShowcaseActivity.class);
                        } else {
                            intent = new Intent(SplashActivity.this, NoNetworkActivity.class);
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        // close this activity
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }, SPLASH_TIME_OUT);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCustomer() throws InterruptedException, ExecutionException {
        SharedPreferences sharedPreferences = getSharedPreferences("Shopnick", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//            sharedPreferences.edit().remove("cid").commit();

        if(TextUtils.isEmpty(sharedPreferences.getString("cid",""))){
            and_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            GetCustomerByCustomerDevIdTask getCustomerByCustomerDevIdTask = new
                    GetCustomerByCustomerDevIdTask(this,and_id);
            getCustomerByCustomerDevIdTask.execute().get();
            Customer customer = getCustomerByCustomerDevIdTask.customerRec;

            if(customer!=null){
                editor.putString("cid", String.valueOf(customer.customer_id));
                editor.apply();
            }
            else{
                Customer sampleCustomer = createSampleCustomer();
                if(sampleCustomer!=null){
                    editor.putString("cid", String.valueOf(sampleCustomer.customer_id));
                    editor.apply();
                }
            }
        }
    }

    private Customer createSampleCustomer() {
        Customer customer = new Customer();

        try {
            Log.d("Tangho",">>>>>Inside createSampleCustome Method");
            Double value                = 10000.0;
            customer.customer_dev_id    = and_id;
            customer.city               = "Austin";
            customer.email              = "john@doe.com";
            customer.first_name         = "John";
            customer.last_name          = "Doe";
            customer.city               = "Ipsum City";
            customer.country            = "Wonderland";
            customer.address            = "15, Lorem Street";
            customer.wallet_value       = value.floatValue();
            customer.zipcode            = "900000";
            customer.mobile             = "9999999999";
            Log.d("Tangho",">>>>>Customer Object filled");

            Customers customers = new Customers();
            customers.customers.add(customer);

            AddCustomerTask addCustomerTask = new AddCustomerTask(this, customers);
            addCustomerTask.execute().get();

            Log.d("Tangho",">>>>> AddCustomerTask completed with customerid > " + addCustomerTask.customerId);
            customer.customer_id        = addCustomerTask.customerId;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
