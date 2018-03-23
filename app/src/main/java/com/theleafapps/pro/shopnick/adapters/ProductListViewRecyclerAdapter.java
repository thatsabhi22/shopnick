package com.theleafapps.pro.shopnick.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.pro.shopnick.models.Product;
import com.theleafapps.pro.shopnick.ui.ProductDetailActivity;
import com.theleafapps.pro.shopnick.utils.MySingleton;

import java.util.List;

/**
 * Created by aviator on 26/04/16.
 */
public class ProductListViewRecyclerAdapter extends
        RecyclerView.Adapter<ProductListViewRecyclerAdapter.ProductListViewHolder> {

    Context mContext;
    List<Product> productList;
    MyProgressDialog myProgressDialog;
    int subCatId;
    int catId;
    private ImageLoader mImageLoader;

    public ProductListViewRecyclerAdapter(Context context, List<Product> productList, int subCatId, int catId, MyProgressDialog myProgressDialog) {
        this.productList = productList;
        this.mContext = context;
        this.subCatId = subCatId;
        this.catId = catId;
        this.myProgressDialog = myProgressDialog;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_cards, null);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView name = (TextView) v.findViewById(R.id.product_name);
                TextView productId = (TextView) v.findViewById(R.id.product_id_textview);

//                Toast.makeText(mContext,"Card Clicked -> Product Name : " + name.getText() + " | Product Id ->"
//                        + productId.getText() , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("productId", productId.getText());
                intent.putExtra("subCatId", subCatId);
                intent.putExtra("categoryId", catId);
                intent.putExtra("title", name.getText());
                MyProgressDialog.show(mContext, myProgressDialog, "", "");
                mContext.startActivity(intent);

            }
        });

        ProductListViewHolder rcv = new ProductListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
        holder.productName.setText(productList.get(position).product_name);
        String url = productList.get(position).image_url.replace(".jpg", "m.jpg");
        holder.productImage.setImageUrl(url, mImageLoader);
        holder.productMrp.setText("Rs " + String.valueOf(productList.get(position).unit_mrp));
        holder.productMrp.setPaintFlags(holder.productMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productOfferPrice.setText("Rs " + productList.get(position).unit_offerprice);
        holder.productId.setText(String.valueOf(productList.get(position).product_id));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public NetworkImageView productImage;
        public TextView productMrp;
        public TextView productOfferPrice;
        public TextView productId;

        public ProductListViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productImage = (NetworkImageView) itemView.findViewById(R.id.product_image);
            productMrp = (TextView) itemView.findViewById(R.id.price_mrp);
            productOfferPrice = (TextView) itemView.findViewById(R.id.price_offer);
            productId = (TextView) itemView.findViewById(R.id.product_id_textview);
        }

    }

}
