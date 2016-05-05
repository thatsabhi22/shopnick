package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.ProductListViewRecyclerAdapter;
import com.theleafapps.shopnick.models.multiples.Products;
import com.theleafapps.shopnick.tasks.GetProductsBySubCatIdTask;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class ProductListActivity extends AppCompatActivity {

    Products productsRec;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.product_list_recycler_view);
            recyclerView.setHasFixedSize(true);

            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

            Intent intent = getIntent();
            int subCatId = Integer.valueOf(intent.getStringExtra("subCatId"));

            if(subCatId > 0) {

                GetProductsBySubCatIdTask getProductsBySubCatIdTask = new GetProductsBySubCatIdTask(this, subCatId);

                boolean x = getProductsBySubCatIdTask.execute().get();

                productsRec = getProductsBySubCatIdTask.productsRec;

                if (productsRec != null && productsRec.products.size() > 0)
                    productsRec.products.size();

                    ProductListViewRecyclerAdapter rcAdapter = new ProductListViewRecyclerAdapter(ProductListActivity.this, productsRec.products);
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
}
