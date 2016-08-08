package com.theleafapps.shopnick.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.dialogs.MoneyAddedDialog;
import com.theleafapps.shopnick.models.Customer;
import com.theleafapps.shopnick.models.multiples.Customers;
import com.theleafapps.shopnick.tasks.GetCustomerByIdTask;
import com.theleafapps.shopnick.tasks.UpdateCustomerWalletValueTask;
import com.theleafapps.shopnick.utils.Commons;
import com.theleafapps.shopnick.utils.Communicator;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddMoneyActivity extends AppCompatActivity implements Communicator{

    Toolbar toolbar;
    Customer customer;
    ActionBar actionBar;
    Button add_wallet_money_button;
    MoneyAddedDialog moneyAddedDialog;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        toolbar         =   (Toolbar) findViewById(R.id.toolbar_addmoney);
        setSupportActionBar(toolbar);

        actionBar       =   getSupportActionBar();
        actionBar.setTitle("Add Money");

        if(!Commons.hasActiveInternetConnection(this)){
            Intent intent1 = new Intent(this,NoNetworkActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        add_wallet_money_button =   (Button) findViewById(R.id.add_wallet_money_button);
        moneyAddedDialog        =   new MoneyAddedDialog();
        fragmentManager         =   getFragmentManager();

        add_wallet_money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent       =   getIntent();
                    int customer_id     =   intent.getIntExtra("customer_id", 0);
                    double cart_total   =   intent.getFloatExtra("cart_total", 0);
                    ArrayList<Integer> cart_item_id_array
                                        =   intent.getIntegerArrayListExtra("cart_item_id_array");

                    if (customer_id != 0) {
                        GetCustomerByIdTask getCustomerByIdTask
                                                =   new GetCustomerByIdTask(AddMoneyActivity.this, customer_id);
                        getCustomerByIdTask.execute().get();

                        customer = getCustomerByIdTask.customerRec;

                        if(customer!=null) {
                            customer.wallet_value   =   10000.0;
                            Customers customersObj  =   new Customers();
                            customersObj.customers.add(customer);

                            UpdateCustomerWalletValueTask updateCustomerWalletValueTask
                                    = new UpdateCustomerWalletValueTask(AddMoneyActivity.this, customersObj);
                            updateCustomerWalletValueTask.execute().get();

                            Bundle arguments = new Bundle();
                            arguments.putInt("cart_total",(int)cart_total);
                            arguments.putIntegerArrayList("cart_item_id_array",cart_item_id_array);
                            moneyAddedDialog.setArguments(arguments);
                            moneyAddedDialog.show(fragmentManager,"Money_Added");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void dialogMessage(String msg) {

    }
}
