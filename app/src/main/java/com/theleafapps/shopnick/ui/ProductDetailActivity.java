package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.Product;
import com.theleafapps.shopnick.models.ProductImage;
import com.theleafapps.shopnick.models.Variant;
import com.theleafapps.shopnick.models.multiples.ProductImages;
import com.theleafapps.shopnick.tasks.GetAllVariantsByProductIdTask;
import com.theleafapps.shopnick.tasks.GetProductByIdTask;
import com.theleafapps.shopnick.tasks.GetAllProductImagesByIdTask;
import com.theleafapps.shopnick.utils.LinkedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    Product productRec;
    ProductImages productImagesRec;
    TextView productName,offer_price,mrp,discount,productDesc,product_available;
    List<String> url_maps;
    SliderLayout sliderShowFull;
    Spinner variantSpinner;
    Toolbar toolbar;
    int subCatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_detail);

            url_maps = new ArrayList<>();
            toolbar = (Toolbar) findViewById(R.id.toolbar_product_detail);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            product_available = (TextView) findViewById(R.id.product_available);
            sliderShowFull = (SliderLayout) findViewById(R.id.product_detail_image);
            productName     =   (TextView) findViewById(R.id.product_detail_name);
            offer_price     =   (TextView) findViewById(R.id.product_detail_offer_price);
            mrp             =   (TextView) findViewById(R.id.product_detail_mrp);
            discount        =   (TextView) findViewById(R.id.product_detail_discount);
            productDesc     =   (TextView) findViewById(R.id.product_detail_desc);
            variantSpinner  =   (Spinner) findViewById(R.id.variant_spinner);

            Intent intent   =   getIntent();
            int productId   =   Integer.valueOf(intent.getStringExtra("productId"));
            subCatId        =   intent.getIntExtra("subCatId",0);

            Toast.makeText(this, "Product id -> " + productId, Toast.LENGTH_LONG).show();

            GetProductByIdTask getProductByIdTask = new GetProductByIdTask(this, productId);
            getProductByIdTask.execute().get();

            productRec = getProductByIdTask.productRec;

            if(productRec != null){

                GetAllProductImagesByIdTask getProductImagesByIdTask = new GetAllProductImagesByIdTask(this, productId);
                getProductImagesByIdTask.execute().get();
                productImagesRec = getProductImagesByIdTask.productImagesRec;

                if(productImagesRec!=null && productImagesRec.productImages.size()>0){

                    for(ProductImage productImages : productImagesRec.productImages){
                        url_maps.add(productImages.image_url);
                    }
                }
                else{
                    url_maps.add(productRec.image_url);
                }

                setTextSliderView();

                setCorrectImageXY();

        /****************************/

                final Map<String,Boolean> variantMap = new LinkedMap<>();
                final List<String> varList = new ArrayList<>();
                GetAllVariantsByProductIdTask getAllVariantsByProductIdTask = new GetAllVariantsByProductIdTask(this,productId);
                getAllVariantsByProductIdTask.execute().get();

                List<Variant> variantList = getAllVariantsByProductIdTask.variantList;

                if(variantList!=null && variantList.size()>0){

                    Log.d("Tangho","Variants Received");

                    for(Variant variant:variantList){
                        variantMap.put(variant.variant_name,variant.available);
                        varList.add(variant.variant_name);
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, varList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                variantSpinner.setAdapter(dataAdapter);

                variantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String variant = varList.get(position);
                        Boolean available = variantMap.get(variant);

                        if(available){
                            product_available.setText("In Stock");
                            product_available.setTextColor(Color.GREEN);
                        }
                        else{
                            product_available.setText("Out Of Stock");
                            product_available.setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        /****************************/

                productName.setText(productRec.product_name);
                offer_price.setText("Rs " + String.valueOf((int)productRec.unit_offerprice));
                mrp.setText("Rs " + String.valueOf((int)productRec.unit_mrp));
                mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                float offerP         =   productRec.unit_offerprice;
                float mrp            =   productRec.unit_mrp;
                float discountValue  =   ((offerP - mrp)/mrp)*100 ;

                discount.setText(String.valueOf((int)discountValue) + " % off");
                productDesc.setText(productRec.product_desc);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private void setTextSliderView() {
        for (String url : url_maps) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
//                    textSliderView.bundle(new Bundle());
//                    textSliderView.getBundle()
//                            .putString("number", String.valueOf(i + 1));

            sliderShowFull.addSlider(textSliderView);
//                    i++;
        }
        sliderShowFull.stopAutoCycle();
    }

    private void setCorrectImageXY() {
        ViewTreeObserver vto = sliderShowFull.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sliderShowFull.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = sliderShowFull.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sliderShowFull.getLayoutParams();
                params.height = width;
                sliderShowFull.setLayoutParams(params);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("subCatId",subCatId);
                NavUtils.navigateUpTo(this,intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
