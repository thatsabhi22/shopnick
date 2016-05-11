package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.ProductListViewRecyclerAdapter;
import com.theleafapps.shopnick.models.multiples.Products;
import com.theleafapps.shopnick.tasks.GetProductsBySubCatIdTask;

import java.util.concurrent.ExecutionException;

public class ProductListActivity extends AppCompatActivity {

    int catId;
    Products productsRec;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private Toolbar toolbar;                                     // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);

            toolbar = (Toolbar) findViewById(R.id.toolbar_product_list);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.product_list_recycler_view);
            recyclerView.setHasFixedSize(true);

            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

            Intent intent   =   getIntent();
            catId           =   intent.getIntExtra("categoryId",0);
            int subCatId    =   intent.getIntExtra("subCatId",0);

            if(subCatId > 0) {

                GetProductsBySubCatIdTask getProductsBySubCatIdTask =
                                        new GetProductsBySubCatIdTask(this, subCatId);

                boolean x = getProductsBySubCatIdTask.execute().get();

                productsRec = getProductsBySubCatIdTask.productsRec;

                if (productsRec != null && productsRec.products.size() > 0)
                    productsRec.products.size();

                    ProductListViewRecyclerAdapter rcAdapter = new ProductListViewRecyclerAdapter(
                            ProductListActivity.this, productsRec.products,subCatId);
                    recyclerView.setAdapter(rcAdapter);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("categoryId",catId);
                NavUtils.navigateUpTo(this,intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
