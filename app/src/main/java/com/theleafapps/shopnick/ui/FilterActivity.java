package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.utils.Commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    Button price_filter_button,discount_filter_button,apply_button;
    RelativeLayout price_filter_layout,discount_filter_layout;
    EditText price_range_low_value,price_range_high_value;
    RadioButton disc_radio,non_disc_radio;
    TextView price_range_msg_tv;
    TabHost filter_tabhost;
    StringBuilder filter_sb;
    Toolbar toolbar;
    List<String> filterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_filter_activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!Commons.hasActiveInternetConnection(this)){
            Intent intent1 = new Intent(this,NoNetworkActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        Intent intent           =   getIntent();
        final int catId         =   intent.getIntExtra("categoryId",0);
        final int subCatId      =   intent.getIntExtra("subCatId",0);

        price_filter_layout     =   (RelativeLayout) findViewById(R.id.price_filter_layout);
        discount_filter_layout  =   (RelativeLayout) findViewById(R.id.discount_filter_layout);
        filter_tabhost          =   (TabHost) findViewById(R.id.filter_tabhost);
        price_filter_button     =   (Button) findViewById(R.id.price_filter_button);
        discount_filter_button  =   (Button) findViewById(R.id.discount_filter_button);
        apply_button            =   (Button) findViewById(R.id.apply_filter_button);
        price_range_low_value   =   (EditText) findViewById(R.id.price_range_low_value);
        price_range_high_value  =   (EditText) findViewById(R.id.price_range_high_value);
        disc_radio              =   (RadioButton) findViewById(R.id.disc_radio);
        non_disc_radio          =   (RadioButton) findViewById(R.id.non_disc_radio);
        price_range_msg_tv      =   (TextView) findViewById(R.id.price_range_msg_tv);
        filterList              =   new ArrayList<>();
        filter_sb               =   new StringBuilder();

        price_filter_layout.setVisibility(View.VISIBLE);
        discount_filter_layout.setVisibility(View.GONE);
        price_range_msg_tv.setVisibility(View.GONE);
        price_filter_button.setBackgroundColor(Color.parseColor("#F48FB1"));

        apply_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //unit_offerprice
                boolean disc_radio_checked      =   false;
                boolean non_disc_radio_checked  =   false;

                String low_val  = price_range_low_value.getText().toString();
                String high_val = price_range_high_value.getText().toString();

                if(disc_radio.isChecked()){
                    disc_radio_checked = true;
                }
                else if(non_disc_radio.isChecked()){
                    non_disc_radio_checked = true;
                }

                filterList.clear();
                price_range_msg_tv.setVisibility(View.GONE);


                //Check for Price range

                //%3E -  (>)
                //%3C -  (<)
                //%3D -  (=)

                if(!TextUtils.isEmpty(low_val) &&
                            !TextUtils.isEmpty(high_val)){

                    if(Integer.parseInt(low_val) >
                            Integer.parseInt(high_val)){
                        price_range_msg_tv.setVisibility(View.VISIBLE);
                        price_range_msg_tv.setText("Higher Range must be greater\n than Lower");
                    }
                    else{
                        filterList.add("(unit_offerprice%3E" + low_val +") AND " +
                                "(unit_offerprice%3C"+ high_val + ")");
                    }
                    Toast.makeText(FilterActivity.this,"Both populated",Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isEmpty(low_val) &&
                        TextUtils.isEmpty(high_val)){
                    filterList.add("(unit_offerprice%3E" + low_val+")");
                    Toast.makeText(FilterActivity.this,"Low yes high no",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(low_val) &&
                        !TextUtils.isEmpty(high_val)){
                    filterList.add("(unit_offerprice%3C" + high_val +")");
                    Toast.makeText(FilterActivity.this,"low no high yes",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(FilterActivity.this,"low no high no",Toast.LENGTH_LONG).show();
                }

                //Check for Discounted Item

                if(disc_radio_checked){
                    filterList.add("(unit_offerprice%3Cunit_mrp)");
                }
                else if(non_disc_radio_checked){
                    filterList.add("(unit_offerprice%3Dunit_mrp)");
                }

                disc_radio.setChecked(false);
                non_disc_radio.setChecked(false);

                String connected = TextUtils.join(" AND ",filterList);

                Intent intent = new Intent(FilterActivity.this,ProductListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fltr_str",connected);
                intent.putExtra("categoryId",catId);
                intent.putExtra("subCatId",subCatId);
                startActivity(intent);
            }
        });
    }


    public void tabHandler(View target){
        price_filter_button.setSelected(false);
        discount_filter_button.setSelected(false);

        if(target.getId() == R.id.price_filter_button){
            filter_tabhost.setCurrentTab(0);
            price_filter_button.setSelected(true);
            price_filter_button.setBackgroundColor(Color.parseColor("#F48FB1"));
            discount_filter_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            price_filter_layout.setVisibility(View.VISIBLE);
            discount_filter_layout.setVisibility(View.GONE);

        } else if(target.getId() == R.id.discount_filter_button){
            filter_tabhost.setCurrentTab(1);
            discount_filter_button.setSelected(true);
            discount_filter_button.setBackgroundColor(Color.parseColor("#F48FB1"));
            price_filter_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            price_filter_layout.setVisibility(View.GONE);
            discount_filter_layout.setVisibility(View.VISIBLE);
        }
    }
}
