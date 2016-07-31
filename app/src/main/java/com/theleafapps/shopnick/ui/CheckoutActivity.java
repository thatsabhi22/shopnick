package com.theleafapps.shopnick.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.dialogs.CouponCodesDialog;
import com.theleafapps.shopnick.models.Coupon;
import com.theleafapps.shopnick.models.Customer;
import com.theleafapps.shopnick.models.multiples.Customers;
import com.theleafapps.shopnick.tasks.DeleteMultipleCartItemsTask;
import com.theleafapps.shopnick.tasks.GetCouponByCodeTask;
import com.theleafapps.shopnick.tasks.GetCustomerByIdTask;
import com.theleafapps.shopnick.tasks.UpdateCustomerWalletValueTask;
import com.theleafapps.shopnick.utils.Commons;
import com.theleafapps.shopnick.utils.Communicator;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckoutActivity extends AppCompatActivity implements Communicator {

    ActionBar actionBar;
    SharedPreferences sharedPreferences;
    Customer customer;
    Toolbar toolbar;
    double wallet_value = 0;
    float cart_total   = 0;
    double deduction   = 0;
    TextView wallet_value_tv,total_amount_value_tv,promocode_result_tv;
    Button confirm_order_button,coupon_code_apply_button;
    CouponCodesDialog couponCodesDialog;
    FragmentManager fragmentManager;
    EditText promocode_edit_text;
    String cid = "";
    List<Integer> cart_item_id_array;
    JSONArray cartItemIdJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cart_item_id_array      =   new ArrayList<>();
        cartItemIdJsonArray     =   new JSONArray();
        Intent intent           =   getIntent();
        cart_total              =   intent.getIntExtra("cart_total",0);
        cart_item_id_array      =   intent.getIntegerArrayListExtra("cart_item_id_array");

        if(cart_item_id_array.size()>0){
             for(int id : cart_item_id_array){
                 cartItemIdJsonArray.put(id);
             }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);

        if(!Commons.hasActiveInternetConnection(this)){
            Intent intent1 = new Intent(this,NoNetworkActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Payment");

        wallet_value_tv         =   (TextView) findViewById(R.id.checkout_wallet_amount_value);
        total_amount_value_tv   =   (TextView) findViewById(R.id.checkout_total_amount_value);
        confirm_order_button    =   (Button)   findViewById(R.id.confirm_order_button);
        coupon_code_apply_button=   (Button)   findViewById(R.id.apply_promocode_button);
        promocode_edit_text     =   (EditText) findViewById(R.id.promocode_edit_text);
        promocode_result_tv     =   (TextView) findViewById(R.id.promocode_result_tv);
        couponCodesDialog       =   new CouponCodesDialog();
        fragmentManager         =   getFragmentManager();

        try {
            sharedPreferences   =   getSharedPreferences("Shopnick", Context.MODE_PRIVATE);
            cid                 =   sharedPreferences.getString("cid", "");

            if (!TextUtils.isEmpty(cid) && Integer.parseInt(cid) != 0) {
                GetCustomerByIdTask getCustomerByIdTask = new GetCustomerByIdTask(this, Integer.parseInt(cid));
                getCustomerByIdTask.execute().get();

                customer        =   getCustomerByIdTask.customerRec;
                wallet_value    =   customer.wallet_value;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        wallet_value_tv.setText(String.valueOf(wallet_value));
        total_amount_value_tv.setText(String.valueOf(cart_total));

        coupon_code_apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupon_string    =   promocode_edit_text.getText().toString();
                double min_cart_value   =   0.0;
                String coupon_type      =   "";
                int value               =   0;

                if(TextUtils.isEmpty(coupon_string)){
                    promocode_result_tv.setText("Please Enter a Promocode");
                    promocode_result_tv.setVisibility(View.VISIBLE);
                    promocode_result_tv.setTextColor(Color.RED);
                }
                else if(coupon_string.length() < 4){
                    promocode_result_tv.setText("Please Enter a Valid Promocode");
                    promocode_result_tv.setVisibility(View.VISIBLE);
                    promocode_result_tv.setTextColor(Color.RED);
                }
                else {
                    GetCouponByCodeTask getCouponByCodeTask = new GetCouponByCodeTask(CheckoutActivity.this,coupon_string);
                    try {
                        getCouponByCodeTask.execute().get();

                        Coupon coupon = getCouponByCodeTask.couponRec;

                        if(coupon == null){
                            promocode_result_tv.setText("Please Enter a Valid Promocode");
                            promocode_result_tv.setVisibility(View.VISIBLE);
                            promocode_result_tv.setTextColor(Color.RED);
                        }
                        else{

                            min_cart_value      =   coupon.min_cart_value;
                            coupon_type         =   coupon.type;
                            value               =   Integer.parseInt(coupon.coupon_value);

                            if(TextUtils.equals(coupon_type,"PER")){
                                if(cart_total > min_cart_value) {
                                    deduction = (cart_total * value) / 100;
                                    promocode_result_tv.setVisibility(View.VISIBLE);
                                    promocode_result_tv.setTextColor(Color.parseColor("#3cb371"));
                                    promocode_result_tv.setText("Rs. " + deduction + " will be deducted from your cart value");
                                    confirm_order_button.setText("Pay Rs. "+ (cart_total-deduction));
                                }else{
                                    promocode_result_tv.setVisibility(View.VISIBLE);
                                    promocode_result_tv.setTextColor(Color.RED);
                                    promocode_result_tv.setText("Coupon is applicable for cart value more than Rs. " + min_cart_value);

                                }
                            }else if(TextUtils.equals(coupon_type,"VAL")){
                                if(cart_total > min_cart_value) {
                                    deduction = value;
                                    promocode_result_tv.setVisibility(View.VISIBLE);
                                    promocode_result_tv.setTextColor(Color.parseColor("#3cb371"));
                                    promocode_result_tv.setText("Rs. " + deduction + " will be deducted from your cart value");
                                    confirm_order_button.setText("Pay Rs. "+ (cart_total-deduction));
                                }else{
                                    promocode_result_tv.setVisibility(View.VISIBLE);
                                    promocode_result_tv.setTextColor(Color.RED);
                                    promocode_result_tv.setText("Coupon is applicable for cart value more than Rs. " + min_cart_value);
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        confirm_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wallet_value > cart_total) {
                    try {
                        customer.wallet_value   =   customer.wallet_value - cart_total - deduction;
                        Customers customersObj  =   new Customers();
                        customersObj.customers.add(customer);

                        UpdateCustomerWalletValueTask updateCustomerWalletValueTask
                                = new UpdateCustomerWalletValueTask(CheckoutActivity.this, customersObj);
                        updateCustomerWalletValueTask.execute().get();

                        DeleteMultipleCartItemsTask deleteMultipleCartItemsTask
                                = new DeleteMultipleCartItemsTask(CheckoutActivity.this,cartItemIdJsonArray);
                        deleteMultipleCartItemsTask.execute().get();

                        Intent intent = new Intent(CheckoutActivity.this, PaymentSuccessActivity.class);
                        intent.putExtra("deduction", deduction);
                        intent.putExtra("net_cost", cart_total - deduction);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Intent intent = new Intent(CheckoutActivity.this, AddMoneyActivity.class);
                    intent.putExtra("customer_id",customer.customer_id);
                    intent.putExtra("cart_total",cart_total);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.coupon_icon:
                couponCodesDialog.show(fragmentManager, "coupon_code");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_checkout_activity, menu);
        return true;
    }

    @Override
    public void dialogMessage(String msg) {

    }
}
