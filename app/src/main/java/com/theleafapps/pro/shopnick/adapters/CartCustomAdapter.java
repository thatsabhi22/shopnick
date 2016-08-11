package com.theleafapps.shopnick.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.dialogs.CartUpdateDialog;
import com.theleafapps.shopnick.dialogs.SimpleDialogClass;
import com.theleafapps.shopnick.models.CartItem;
import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.utils.MySingleton;

import java.util.List;

/**
 * Created by aviator on 08/07/16.
 */
public class CartCustomAdapter extends RecyclerView.Adapter<CartCustomAdapter.MyViewHolder>  implements View.OnClickListener{

    Context mContext;
    CartItems cartItems;
    CartItem current;
    List<CartItem> cartItemList;
    LayoutInflater inflater;
    ImageLoader mImageLoader;
    FragmentManager fragmentManager;
    int product_id;
    String product_name_bundle;

    public CartCustomAdapter(Context context, CartItems cartItems, FragmentManager fragmentManager) {
        inflater                =   LayoutInflater.from(context);
        this.mContext           =   context;
        this.cartItems          =   cartItems;
        this.fragmentManager    =   fragmentManager;

        if(this.cartItems!=null)
            this.cartItemList   =   cartItems.cartItemList;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.single_row_cart,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        current = cartItemList.get(position);

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();

        if(!TextUtils.isEmpty(current.product.image_url)){
            String image_url = current.product.image_url;
            image_url = image_url.replace(".jpg","s.jpg");
            current.product.image_url = image_url;
        }

        holder.cart_product_image.setImageUrl(current.product.image_url,mImageLoader);
        holder.product_name.setText(current.product.product_name);
        holder.product_size.setText(current.variant);
        holder.product_qty.setText(String.valueOf(current.quantity));
        holder.product_offerprice.setText(String.valueOf(current.product.unit_offerprice));
        holder.multiply_product_qty.setText(String.valueOf(current.quantity));
        holder.net_cost.setText(String.valueOf(current.product.unit_offerprice * current.quantity));
        holder.cart_item_id.setText(String.valueOf(current.cart_item_id));
        holder.unit_shipping.setText(String.valueOf(current.product.unit_shipping));
        product_id          =   current.product_id;
        product_name_bundle =   current.product.product_name;
    }

    @Override
    public int getItemCount() {
        if(cartItems!=null && cartItems.cartItemList.size() != 0) {
            return cartItems.cartItemList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView cart_product_image;
        ImageView delete_cart_item,update_cart_item;
        TextView product_name,product_size,product_qty,unit_shipping;
        TextView net_cost,product_offerprice,multiply_product_qty;
        TextView cart_item_id;
        SimpleDialogClass simpleDialog;
        CartUpdateDialog cartUpdateDialog;
        Bundle bundle;


        public MyViewHolder(View itemView) {
            super(itemView);
            bundle                  =   new Bundle();
            simpleDialog            =   new SimpleDialogClass();
            cartUpdateDialog        =   new CartUpdateDialog();
            delete_cart_item        =   (ImageView) itemView.findViewById(R.id.deleteCartItemButton);
            update_cart_item        =   (ImageView) itemView.findViewById(R.id.updateCartItemButton);
            cart_product_image      =   (NetworkImageView) itemView.findViewById(R.id.cartItemImageView);
            product_name            =   (TextView) itemView.findViewById(R.id.product_name_value);
            product_size            =   (TextView) itemView.findViewById(R.id.size_value);
            product_qty             =   (TextView) itemView.findViewById(R.id.product_quantity_value);
            product_offerprice      =   (TextView) itemView.findViewById(R.id.offer_price_value);
            multiply_product_qty    =   (TextView) itemView.findViewById(R.id.multiply_product_qty);
            net_cost                =   (TextView) itemView.findViewById(R.id.net_cost);
            unit_shipping           =   (TextView) itemView.findViewById(R.id.unit_shipping_value);
            cart_item_id            =   (TextView) itemView.findViewById(R.id.cart_item_id);


            delete_cart_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putInt("cart_item_id",Integer.parseInt(cart_item_id.getText().toString()));
                    simpleDialog.setArguments(bundle);
                    simpleDialog.show(fragmentManager, "delete_cart_item");
                }
            });

            update_cart_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putInt("cart_item_id",Integer.parseInt(cart_item_id.getText().toString()));
                    bundle.putInt("product_id",product_id);
                    bundle.putString("product_name",product_name_bundle);
                    cartUpdateDialog.setArguments(bundle);
                    cartUpdateDialog.show(fragmentManager, "update_cart_item");
                }
            });
        }
    }
}
