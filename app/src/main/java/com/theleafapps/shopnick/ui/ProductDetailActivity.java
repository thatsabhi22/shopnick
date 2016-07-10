package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.CartItem;
import com.theleafapps.shopnick.models.Product;
import com.theleafapps.shopnick.models.ProductImage;
import com.theleafapps.shopnick.models.Variant;
import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.models.multiples.ProductImages;
import com.theleafapps.shopnick.tasks.AddNewCartItemTask;
import com.theleafapps.shopnick.tasks.GetAllVariantsByProductIdTask;
import com.theleafapps.shopnick.tasks.GetProductByIdTask;
import com.theleafapps.shopnick.tasks.GetAllProductImagesByIdTask;
import com.theleafapps.shopnick.utils.LinkedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProductDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    Product productRec;
    ProductImages productImagesRec;
    TextView productName,offer_price,mrp,discount,productDesc,product_avlble;
    List<String> url_maps;
    SliderLayout sliderShowFull;
    Spinner variantSpinner,quantitySpinner;
    Toolbar toolbar;
    String size,variant;
    int subCatId,quantity;
    ImageButton buyNowButton;
    RelativeLayout variantLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_detail);

            url_maps = new ArrayList<>();
            toolbar = (Toolbar) findViewById(R.id.toolbar_product_detail);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            product_avlble  =   (TextView) findViewById(R.id.product_available);
            sliderShowFull  =   (SliderLayout) findViewById(R.id.product_detail_image);
            productName     =   (TextView) findViewById(R.id.product_detail_name);
            offer_price     =   (TextView) findViewById(R.id.product_detail_offer_price);
            mrp             =   (TextView) findViewById(R.id.product_detail_mrp);
            discount        =   (TextView) findViewById(R.id.product_detail_discount);
            productDesc     =   (TextView) findViewById(R.id.product_detail_desc);
            variantSpinner  =   (Spinner) findViewById(R.id.variant_spinner);
            quantitySpinner =   (Spinner) findViewById(R.id.quantity_spinner);
            buyNowButton    =   (ImageButton) findViewById(R.id.buyNowButton);
            variantLayout   =   (RelativeLayout) findViewById(R.id.variant_layout);


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

        /************** Setting The Variant Spinner *****************/

                final Map<String,Boolean> variantMap = new LinkedMap<>();
                final List<String> varList = new ArrayList<>();
                ArrayAdapter<String> dataAdapter;
                GetAllVariantsByProductIdTask getAllVariantsByProductIdTask = new GetAllVariantsByProductIdTask(this,productId);
                getAllVariantsByProductIdTask.execute().get();

                final List<Variant> variantList = getAllVariantsByProductIdTask.variantList;

                if(variantList!=null && variantList.size()>0) {

                    Log.d("Tangho", "Variants Received");

                    for (Variant variant : variantList) {
                        variantMap.put(variant.variant_name, variant.available);
                        varList.add(variant.variant_name);
                    }

                    dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, varList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    variantSpinner.setAdapter(dataAdapter);

                    variantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            size                =   varList.get(position);
                            Boolean available   =   variantMap.get(size);

                            if (available) {
                                product_avlble.setText("In Stock");
                                product_avlble.setTextColor(Color.GREEN);
                                variant = size;
                            } else {
                                product_avlble.setText("Out Of Stock");
                                product_avlble.setTextColor(Color.RED);
                                variant = "";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else{
                    //variantLayout.setVisibility(View.GONE);
                }

        /*************************************************************/
        /************** Setting The Quantity Spinner *****************/

                final List<String> quantityList = new ArrayList<>();
                for(int i=1;i<=10;i++){
                    quantityList.add(String.valueOf(i));
                }

                ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, quantityList);
                quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                quantitySpinner.setAdapter(quantityAdapter);
                quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        quantity = Integer.valueOf(quantityList.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        /*************************************************************/

                productName.setText(productRec.product_name);
                offer_price.setText("Rs " + String.valueOf((int)productRec.unit_offerprice));
                mrp.setText("Rs " + String.valueOf((int)productRec.unit_mrp));
                mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                float offerP         =   productRec.unit_offerprice;
                float mrp            =   productRec.unit_mrp;
                float discountValue  =   ((offerP - mrp)/mrp)*100 ;

                discount.setText(String.valueOf((int)discountValue) + " % off");
                productDesc.setText(productRec.product_desc);

                buyNowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TextUtils.isEmpty(variant)){
                            CartItem cartItem       =   new CartItem();
                            cartItem.product_id     =   productRec.product_id;
                            cartItem.quantity       =   quantity;
                            cartItem.variant        =   variant;
                            cartItem.customer_id    =   1;

                            CartItems cartItems = new CartItems();
                            cartItems.cartItemList.add(cartItem);

                            AddNewCartItemTask addNewCartItemTask = new AddNewCartItemTask(ProductDetailActivity.this,cartItems);

                            try {

                                addNewCartItemTask.execute().get();
                                Toast.makeText(ProductDetailActivity.this,
                                        productRec.product_name+" has been added to you cart",Toast.LENGTH_LONG).show();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(ProductDetailActivity.this,"The selected product is " +
                                    "out of stock" ,Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
            case R.id.cart_icon:
                Toast.makeText(this,"Cart Menu Clicked",Toast.LENGTH_LONG).show();
                Intent in = new Intent(this,CartActivity.class);
                startActivity(in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
