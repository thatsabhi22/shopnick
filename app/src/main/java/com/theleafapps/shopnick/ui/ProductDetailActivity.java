package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.graphics.Paint;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.GalleryPagerAdapter;
import com.theleafapps.shopnick.models.Product;
import com.theleafapps.shopnick.tasks.GetProductByIdTask;
import com.theleafapps.shopnick.utils.MySingleton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProductDetailActivity extends AppCompatActivity {

    Product productRec;
    @InjectView(R.id.product_detail_name) TextView productName;
    @InjectView(R.id.product_detail_offer_price)TextView offer_price;
    @InjectView(R.id.product_detail_mrp)TextView mrp;
    @InjectView(R.id.product_detail_discount)TextView discount;
    @InjectView(R.id.product_detail_desc)TextView productDesc;
//    @InjectView(R.id.product_detail_image) NetworkImageView productImage;
    @InjectView(R.id.toolbar_product_detail)Toolbar toolbar;
    private ImageLoader mImageLoader;
    int subCatId;

    private GalleryPagerAdapter _adapter;
    @InjectView(R.id.pager)ViewPager _pager;
    @InjectView(R.id.thumbnails)LinearLayout _thumbnails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_detail);
            ButterKnife.inject(this);

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            _adapter = new GalleryPagerAdapter(this,_pager,_thumbnails);
            _pager.setAdapter(_adapter);
            _pager.setOffscreenPageLimit(6); // how many images to load into memory


            Intent intent   =   getIntent();
            int productId   =   Integer.valueOf(intent.getStringExtra("productId"));
            subCatId        =   intent.getIntExtra("subCatId",0);

            Toast.makeText(this, "Product id -> " + productId, Toast.LENGTH_LONG).show();

            GetProductByIdTask getProductByIdTask = new GetProductByIdTask(this, productId);
            getProductByIdTask.execute().get();

            productRec = getProductByIdTask.productRec;

            if(productRec != null){

                mImageLoader = MySingleton.getInstance(this).getImageLoader();
                productName.setText(productRec.product_name);
//                productImage.setImageUrl(productRec.image_url,mImageLoader);
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
}
