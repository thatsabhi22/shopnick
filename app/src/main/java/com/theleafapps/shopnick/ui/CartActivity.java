package com.theleafapps.shopnick.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.CartCustomAdapter;
import com.theleafapps.shopnick.dialogs.SimpleDialogClass;
import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.tasks.GetAllCartItemTask;

import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity implements SimpleDialogClass.Communicator {

    Toolbar toolbar;
    CartCustomAdapter cartCustomAdapter;
    RecyclerView cartRecyclerView;
    CartItems cartItems;
    ImageButton continue_shop_btn;
    TextView emptycart_tv;
    FragmentManager fragmentManager;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext            =   CartActivity.this;
        continue_shop_btn   =   (ImageButton)   findViewById(R.id.continue_shopping);
        emptycart_tv        =   (TextView)      findViewById(R.id.emptycart_tv);
        cartRecyclerView    =   (RecyclerView)  findViewById(R.id.cart_recycler_view);
        cartItems           =   getCartItems();
        fragmentManager     =   getFragmentManager();

        if(cartItems != null && cartItems.cartItemList.size() > 0){
            reloadCartList();
        }
        else{
            setEmptyCart();
        }
    }

    private void setEmptyCart() {

        cartRecyclerView.setVisibility(View.GONE);
        emptycart_tv.setVisibility(View.VISIBLE);
        continue_shop_btn.setVisibility(View.VISIBLE);

        continue_shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,ShowcaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void reloadCartList() {
        cartRecyclerView.setVisibility(View.VISIBLE);
        emptycart_tv.setVisibility(View.GONE);
        continue_shop_btn.setVisibility(View.GONE);

        cartCustomAdapter = new CartCustomAdapter(this,cartItems,fragmentManager);
        cartRecyclerView.setAdapter(cartCustomAdapter);

        final LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cartRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

    private CartItems getCartItems(){

        try{
            GetAllCartItemTask getAllCartItemTask = new GetAllCartItemTask(this);
            getAllCartItemTask.execute().get();

            return getAllCartItemTask.cartItemsReceived;

        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void dialogMessage(String msg) {
        cartCustomAdapter.notifyDataSetChanged();
        cartItems = getCartItems();

        if(cartItems.cartItemList.size() > 0)
            reloadCartList();
        else
            setEmptyCart();

    }
}
