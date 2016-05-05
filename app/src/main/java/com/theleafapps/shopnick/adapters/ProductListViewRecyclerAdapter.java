package com.theleafapps.shopnick.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.Product;
import com.theleafapps.shopnick.utils.MySingleton;

import java.util.List;

/**
 * Created by aviator on 26/04/16.
 */
public class ProductListViewRecyclerAdapter extends RecyclerView.Adapter<ProductListViewRecyclerAdapter.ProductListViewHolder>{

    Context mContext;
    List<Product> productList;
    private ImageLoader mImageLoader;

    public ProductListViewRecyclerAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.mContext = context;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_cards, null);
        ProductListViewHolder rcv = new ProductListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
        holder.productName.setText(productList.get(position).product_name);
        holder.productImage.setImageUrl(productList.get(position).image_url,mImageLoader);
        holder.productMrp.setText("Rs " + String.valueOf(productList.get(position).unit_mrp));
        holder.productMrp.setPaintFlags(holder.productMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productOfferPrice.setText("Rs " + productList.get(position).unit_offerprice);
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder{

        public TextView productName;
        public NetworkImageView productImage;
        public TextView productMrp;
        public TextView productOfferPrice;

        public ProductListViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            productName         =   (TextView) itemView.findViewById(R.id.product_name);
            productImage        =   (NetworkImageView) itemView.findViewById(R.id.product_image);
            productMrp          =   (TextView) itemView.findViewById(R.id.price_mrp);
            productOfferPrice   =   (TextView) itemView.findViewById(R.id.price_offer);
        }

    }

}
