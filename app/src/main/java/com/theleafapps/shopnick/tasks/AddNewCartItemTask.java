package com.theleafapps.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 30/06/16.
 */
public class AddNewCartItemTask extends BaseAsyncRequest {

    Context context;
    public int cartItemId;
    CartItems cartItems;

    public AddNewCartItemTask(Context context, CartItems cartItems){
        this.context        =   context;
        this.cartItems      =   cartItems;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "AddNewCartItemTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "cart_item";

        verb = "POST";

        requestString = ApiInvoker.serialize(cartItems).replace("\"cart_item_id\":0,","");

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        // response has whole contact record, but we just want the id
        CartItems cartItems   =   (CartItems) ApiInvoker.deserialize(response, "", CartItems.class);
        cartItemId            =   cartItems.cartItemList.get(0).cart_item_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success) {
            Log.d("Tang Ho","Success");
        }
    }
}
