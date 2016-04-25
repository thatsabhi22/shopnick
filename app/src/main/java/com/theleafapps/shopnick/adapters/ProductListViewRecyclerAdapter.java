package com.theleafapps.shopnick.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aviator on 26/04/16.
 */
public class ProductListViewRecyclerAdapter extends RecyclerView.Adapter<ProductListViewRecyclerAdapter.ProductListViewHolder>{

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder{

        public ProductListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
