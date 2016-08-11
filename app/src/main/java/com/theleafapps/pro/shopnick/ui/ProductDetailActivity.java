package com.theleafapps.pro.shopnick.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.pro.shopnick.models.CartItem;
import com.theleafapps.pro.shopnick.models.Product;
import com.theleafapps.pro.shopnick.models.ProductImage;
import com.theleafapps.pro.shopnick.models.Variant;
import com.theleafapps.pro.shopnick.models.multiples.CartItems;
import com.theleafapps.pro.shopnick.models.multiples.ProductImages;
import com.theleafapps.pro.shopnick.tasks.AddCartItemTask;
import com.theleafapps.pro.shopnick.tasks.GetAllVariantsByProductIdTask;
import com.theleafapps.pro.shopnick.tasks.GetProductByIdTask;
import com.theleafapps.pro.shopnick.tasks.GetAllProductImagesByIdTask;
import com.theleafapps.pro.shopnick.utils.Commons;
import com.theleafapps.pro.shopnick.utils.LinkedMap;

import java.net.MalformedURLException;
import java.net.URL;
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
    MyProgressDialog myProgressDialog;
    Spinner variantSpinner,quantitySpinner;
    Toolbar toolbar;
    String size,variant,title;
    int subCatId,catId,quantity,productId;
    ImageButton buyNowButton;
    RelativeLayout variantLayout;
    Menu menu;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_detail);

            //Commons.getActivityTrail(this);
            myProgressDialog = new MyProgressDialog(this);
            if (menu != null) {
                menuItem = menu.findItem(R.id.cart_icon);

                if (Commons.cartItemCount > 0)
                    menuItem.setIcon(R.drawable.cartfull);
                else
                    menuItem.setIcon(R.drawable.cart);
            }

            url_maps    =   new ArrayList<>();
            toolbar     =   (Toolbar) findViewById(R.id.toolbar_product_detail);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.logo_small);

            if (!Commons.hasActiveInternetConnection(this)) {
                Intent intent1 = new Intent(this, NoNetworkActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }

            product_avlble  = (TextView) findViewById(R.id.product_available);
            sliderShowFull  = (SliderLayout) findViewById(R.id.product_detail_image);
            productName     = (TextView) findViewById(R.id.product_detail_name);
            offer_price     = (TextView) findViewById(R.id.product_detail_offer_price);
            mrp             = (TextView) findViewById(R.id.product_detail_mrp);
            discount        = (TextView) findViewById(R.id.product_detail_discount);
            productDesc     = (TextView) findViewById(R.id.product_detail_desc);
            variantSpinner  = (Spinner) findViewById(R.id.variant_spinner);
            quantitySpinner = (Spinner) findViewById(R.id.quantity_spinner);
            buyNowButton    = (ImageButton) findViewById(R.id.buyNowButton);
            variantLayout   = (RelativeLayout) findViewById(R.id.variant_layout);


            Intent intent   = getIntent();
            productId       = Integer.valueOf(intent.getStringExtra("productId"));
            subCatId        = intent.getIntExtra("subCatId", 0);
            catId           = intent.getIntExtra("categoryId", 0);
            title           = intent.getStringExtra("title");

            if (!TextUtils.isEmpty(title)) {
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
                getSupportActionBar().setTitle(title);
            }

//            Toast.makeText(this, "Product id -> " + productId, Toast.LENGTH_LONG).show();

            GetProductByIdTask getProductByIdTask = new GetProductByIdTask(this, productId);
            getProductByIdTask.execute().get();

            productRec = getProductByIdTask.productRec;

            if (productRec != null) {

                GetAllProductImagesByIdTask getProductImagesByIdTask = new GetAllProductImagesByIdTask(this, productId);

                boolean x        =  getProductImagesByIdTask.execute().get();
                productImagesRec =  getProductImagesByIdTask.productImagesRec;

                if (productImagesRec != null && productImagesRec.productImages.size() > 0) {

                    for (ProductImage productImages : productImagesRec.productImages) {

                        try{
                            URL url = new URL(productImages.image_url);
                            url_maps.add(productImages.image_url);
                        }catch (MalformedURLException ex) {

                        }
                    }

                    if(url_maps.size()==0){
                        url_maps.add(productRec.image_url);
                    }

                } else {
                    url_maps.add(productRec.image_url);
                }

                setTextSliderView();
                setCorrectImageXY();

                /************** Setting The Variant Spinner *****************/

                final Map<String, Boolean> variantMap = new LinkedMap<>();
                final List<String> varList = new ArrayList<>();
                ArrayAdapter<String> dataAdapter;
                GetAllVariantsByProductIdTask getAllVariantsByProductIdTask = new GetAllVariantsByProductIdTask(this, productId);
                getAllVariantsByProductIdTask.execute().get();

                final List<Variant> variantList = getAllVariantsByProductIdTask.variantList;

                if (variantList != null && variantList.size() > 0) {

                    Log.d("Tangho", "Variants Received");

                    for (Variant variant : variantList) {
                        variantMap.put(variant.variant_name, variant.available);
                        varList.add(variant.variant_name);
                    }
                } else {
                    varList.add("Standard");
                    variantMap.put("Standard", true);
                }

                dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, varList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                variantSpinner.setAdapter(dataAdapter);

                variantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        size = varList.get(position);
                        Boolean available = variantMap.get(size);

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
            } else {
                //variantLayout.setVisibility(View.GONE);
            }

            /*************************************************************/
            /************** Setting The Quantity Spinner *****************/

            final List<String> quantityList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
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
            offer_price.setText("Rs " + String.valueOf((int) productRec.unit_offerprice));
            mrp.setText("Rs " + String.valueOf((int) productRec.unit_mrp));
            mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            float offerP = productRec.unit_offerprice;
            float mrp = productRec.unit_mrp;
            float discountValue = ((offerP - mrp) / mrp) * 100;

            discount.setText(String.valueOf((int) discountValue) + " % off");
            productDesc.setText(productRec.product_desc);

            buyNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!TextUtils.isEmpty(variant)) {
                        CartItem cartItem    = new CartItem();
                        cartItem.product_id  = productRec.product_id;
                        cartItem.quantity    = quantity;
                        cartItem.variant     = variant;

                        SharedPreferences sharedPreferences
                                             = getSharedPreferences("Shopnick", Context.MODE_PRIVATE);
                        cartItem.customer_id = Integer.valueOf(sharedPreferences.getString("cid", ""));

                        CartItems cartItems  = new CartItems();
                        cartItems.cartItemList.add(cartItem);

                        AddCartItemTask addCartItemTask
                                             = new AddCartItemTask(ProductDetailActivity.this, cartItems);

                        try {

                            addCartItemTask.execute().get();
                            Toast.makeText(ProductDetailActivity.this,
                                    productRec.product_name + " has been added to you cart", Toast.LENGTH_LONG).show();
                            menuItem = menu.findItem(R.id.cart_icon);
                            menuItem.setIcon(R.drawable.cartfull);
                            Commons.cartItemCount++;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "The selected product is " +
                                "out of stock", Toast.LENGTH_LONG).show();
                    }
                }
            });

            if(Commons.myProgressDialog!=null)
                Commons.myProgressDialog.dismiss();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(Exception ex){
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
        ViewTreeObserver vto    =   sliderShowFull.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sliderShowFull.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width       =   sliderShowFull.getMeasuredWidth();
                RelativeLayout.LayoutParams params
                                =   (RelativeLayout.LayoutParams) sliderShowFull.getLayoutParams();
                params.height   =   width;
                sliderShowFull.setLayoutParams(params);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Tangho","ProductDetailActivity activity >> onResume Called");
        if(menu!=null) {
            menuItem = menu.findItem(R.id.cart_icon);
            if (Commons.cartItemCount < 1) {
                menuItem.setIcon(R.drawable.cart);
            } else {
                menuItem.setIcon(R.drawable.cartfull);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.user_profile:
                intent = new Intent(this,CustomerProfileActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                loadProductListActivity();
                return true;
            case R.id.cart_icon:
                //Toast.makeText(this,"Cart Menu Clicked",Toast.LENGTH_LONG).show();
                Intent in = new Intent(this,CartActivity.class);
                in.putExtra("caller","ProductDetailActivity");
                in.putExtra("subCatId",subCatId);
                in.putExtra("categoryId",catId);
                in.putExtra("productId",productId);
                in.putExtra("title",title);
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
        MenuItem menuItem = menu.findItem(R.id.cart_icon);
        this.menu = menu;
        if(Commons.cartItemCount>0)
            menuItem.setIcon(R.drawable.cartfull);
        else
            menuItem.setIcon(R.drawable.cart);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public void loadProductListActivity(){
        Intent intent;
        intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("subCatId",subCatId);
        intent.putExtra("categoryId",catId);
        MyProgressDialog.show(this,myProgressDialog,"","");
        NavUtils.navigateUpTo(this,intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadProductListActivity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Tangho","ProductDetail activity >> onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Tangho","ProductDetail activity >> onPause Called");
    }

    @Override
    protected void onDestroy() {
        myProgressDialog.dismiss();
        super.onDestroy();
        Log.d("Tangho","ProductDetail activity >> onDestroy Called");
    }
}
