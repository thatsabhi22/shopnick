package com.theleafapps.pro.shopnick.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.adapters.ProductListViewRecyclerAdapter;
import com.theleafapps.pro.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.pro.shopnick.models.SubCategory;
import com.theleafapps.pro.shopnick.models.multiples.Products;
import com.theleafapps.pro.shopnick.tasks.GetProductsBySubCatIdTask;
import com.theleafapps.pro.shopnick.tasks.GetSubCatByIdTask;
import com.theleafapps.pro.shopnick.utils.Commons;

import java.util.concurrent.ExecutionException;

public class ProductListActivity extends AppCompatActivity {

    int catId,subCatId;
    String sort_str,fltr_str,title;
    Products productsRec;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Toolbar toolbar;                                     // Declaring the Toolbar Object
    RecyclerView recyclerView;
    LinearLayout filter_section_layout;
    TextView sort_tv,filter_tv,no_product_tv;
    MyProgressDialog myProgressDialog;
    Menu menu;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            myProgressDialog    =   new MyProgressDialog(this);
            setContentView(R.layout.activity_product_list);
            //Commons.getActivityTrail(this);

            toolbar = (Toolbar) findViewById(R.id.toolbar_product_list);
            setSupportActionBar(toolbar);

            if(menu!=null) {
                menuItem = menu.findItem(R.id.cart_icon);
                if (Commons.cartItemCount > 0)
                    menuItem.setIcon(R.drawable.cartfull);
                else
                    menuItem.setIcon(R.drawable.cart);
            }

            if(!Commons.hasActiveInternetConnection(this)){
                Intent intent1 = new Intent(this,NoNetworkActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }

            sort_tv                     =   (TextView) findViewById(R.id.product_list_sort_tv);
            filter_tv                   =   (TextView) findViewById(R.id.product_list_filter_tv);
            filter_section_layout       =   (LinearLayout) findViewById(R.id.filter_section_layout);
            no_product_tv               =   (TextView) findViewById(R.id.no_product_tv);
            filter_section_layout.setVisibility(View.GONE);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.logo_small);

            recyclerView = (RecyclerView)findViewById(R.id.product_list_recycler_view);
            recyclerView.setHasFixedSize(true);

            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

            final Intent intent =   getIntent();
            catId               =   intent.getIntExtra("categoryId",0);
            subCatId            =   intent.getIntExtra("subCatId",0);
            sort_str            =   intent.getStringExtra("sort_str");
            fltr_str            =   intent.getStringExtra("fltr_str");
            title               =   intent.getStringExtra("title");

            if(TextUtils.isEmpty(title)){
                GetSubCatByIdTask getSubCatByIdTask = new GetSubCatByIdTask(this,subCatId);
                getSubCatByIdTask.execute().get();

                SubCategory subCategory = getSubCatByIdTask.subCategoryRec;

                if(subCategory!=null){
                    title = subCategory.sub_category_name;
                }
            }
            getSupportActionBar().setTitle(title);

            if(subCatId > 0) {

                GetProductsBySubCatIdTask getProductsBySubCatIdTask =
                        new GetProductsBySubCatIdTask(this, subCatId, sort_str, fltr_str);
                boolean x = getProductsBySubCatIdTask.execute().get();
                productsRec = getProductsBySubCatIdTask.productsRec;

                if (productsRec != null && productsRec.products.size() > 0) {
                    ProductListViewRecyclerAdapter rcAdapter = new ProductListViewRecyclerAdapter(
                            ProductListActivity.this, productsRec.products, subCatId,catId,myProgressDialog);
                    recyclerView.setAdapter(rcAdapter);
                }else{
                    no_product_tv.setVisibility(View.VISIBLE);
                }

                filter_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductListActivity.this, FilterActivity.class);
                        intent.putExtra("categoryId", catId);
                        intent.putExtra("subCatId", subCatId);
                        intent.putExtra("title",title);
                        startActivity(intent);
                    }
                });

                sort_tv.setOnClickListener(new View.OnClickListener() {
                    StringBuilder sort_str = new StringBuilder();

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
                        builder.setTitle("Make your selection");
                        builder.setItems(Commons.sort_options, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int item) {
                                // Do something with the selection
                                // Toast.makeText(ProductListActivity.this, "Dikha...." + item, Toast.LENGTH_LONG).show();
                                switch (item) {
                                    case 1:
                                        sort_str.append("unit_offerprice%20ASC");
                                        break;
                                    case 2:
                                        sort_str.append("unit_offerprice%20DESC");
                                        break;
                                    case 3:
                                        sort_str.append("((unit_offerprice-unit_mrp)/unit_mrp)");
                                        break;
                                    default:
                                        sort_str.append("");
                                }
                                    dialog.dismiss();
                                    Intent intent = new Intent(ProductListActivity.this, ProductListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("sort_str", sort_str.toString());
                                    intent.putExtra("categoryId", catId);
                                    intent.putExtra("subCatId", subCatId);
                                    intent.putExtra("title",title);
                                    startActivity(intent);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Tangho","ProductList activity >> onResume Called");
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
    public void onBackPressed() {
        super.onBackPressed();
        loadShowCase();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Tangho","ProductList activity >> onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Tangho","ProductList activity >> onPause Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Tangho","ProductList activity >> onDestroy Called");
        myProgressDialog.dismiss();
    }

    private void loadShowCase() {
        Intent intent = new Intent(this,ShowcaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("categoryId",catId-1);
//        MyProgressDialog.show(this,myProgressDialog,"","");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_list, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_icon);
        this.menu = menu;
        if(Commons.cartItemCount>0)
            menuItem.setIcon(R.drawable.cartfull);
        else
            menuItem.setIcon(R.drawable.cart);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                loadShowCase();
                return true;
            case R.id.user_profile:
                intent = new Intent(this,CustomerProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart_icon:
                intent = new Intent(this,CartActivity.class);
                intent.putExtra("caller","ProductListActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("subCatId",subCatId);
                intent.putExtra("categoryId",catId);
                startActivity(intent);
                finish();
                return true;
            case R.id.filter_menu:
                if(filter_section_layout.getVisibility()== View.GONE)
                    filter_section_layout.setVisibility(View.VISIBLE);
                else if(filter_section_layout.getVisibility()== View.VISIBLE)
                    filter_section_layout.setVisibility(View.GONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
