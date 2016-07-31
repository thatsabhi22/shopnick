package com.theleafapps.shopnick.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.ProductListViewRecyclerAdapter;
import com.theleafapps.shopnick.models.multiples.Products;
import com.theleafapps.shopnick.tasks.GetProductsBySubCatIdTask;
import com.theleafapps.shopnick.utils.Commons;

import java.util.concurrent.ExecutionException;

public class ProductListActivity extends AppCompatActivity {

    int catId,subCatId;
    String sort_str,fltr_str,title;
    Products productsRec;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Toolbar toolbar;                                     // Declaring the Toolbar Object
    RecyclerView recyclerView;
    LinearLayout filter_section_layout;
    TextView sort_tv,filter_tv;
    Menu menu;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);

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

            sort_tv                 =   (TextView) findViewById(R.id.product_list_sort_tv);
            filter_tv               =   (TextView) findViewById(R.id.product_list_filter_tv);
            filter_section_layout   =   (LinearLayout) findViewById(R.id.filter_section_layout);
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

            getSupportActionBar().setTitle(title);

            if(subCatId > 0) {

                GetProductsBySubCatIdTask getProductsBySubCatIdTask =
                        new GetProductsBySubCatIdTask(this, subCatId, sort_str, fltr_str);
                boolean x = getProductsBySubCatIdTask.execute().get();
                productsRec = getProductsBySubCatIdTask.productsRec;

                if (productsRec != null && productsRec.products.size() > 0) {
                    ProductListViewRecyclerAdapter rcAdapter = new ProductListViewRecyclerAdapter(
                            ProductListActivity.this, productsRec.products, subCatId);
                    recyclerView.setAdapter(rcAdapter);
                }

                filter_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductListActivity.this, FilterActivity.class);
                        intent.putExtra("categoryId", catId);
                        intent.putExtra("subCatId", subCatId);
                        startActivity(intent);
                    }
                });

                sort_tv.setOnClickListener(new View.OnClickListener() {
                    StringBuilder sort_str = new StringBuilder();

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
                        builder.setTitle("Make your selection");
                        builder.setCancelable(false);
                        builder.setItems(Commons.sort_options, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int item) {
                                // Do something with the selection
                                Toast.makeText(ProductListActivity.this, "Dikha...." + item, Toast.LENGTH_LONG).show();
                                switch (item) {
                                    case 1:
                                        sort_str.append("unit_offerprice ASC");
                                        break;
                                    case 2:
                                        sort_str.append("unit_offerprice DESC");
                                        break;
                                    case 3:
                                        sort_str.append("((unit_offerprice-unit_mrp)/unit_mrp)");
                                        break;
                                    default:
                                        sort_str.append("");
                                }
                                dialog.dismiss();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(ProductListActivity.this, ProductListActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("sort_str", sort_str.toString());
                                        intent.putExtra("categoryId", catId);
                                        intent.putExtra("subCatId", subCatId);
                                        startActivity(intent);
                                    }
                                }, 1000);
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
                intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("categoryId",catId);
                NavUtils.navigateUpTo(this,intent);
                return true;
            case R.id.user_profile:
                intent = new Intent(this,CustomerProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart_icon:
                intent = new Intent(this,CartActivity.class);
                startActivity(intent);
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
