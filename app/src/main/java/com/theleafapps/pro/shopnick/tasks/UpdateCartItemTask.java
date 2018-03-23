package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.CartItems;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 11/07/16.
 */
public class UpdateCartItemTask extends BaseAsyncRequest {

    public int cartItemId;
    Context context;
    CartItems cartItems;

    public UpdateCartItemTask(Context context, CartItems cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "UpdateCartItemTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "cart_item";

        verb = "PUT";

        requestString = ApiInvoker.serialize(cartItems);

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        CartItems cartItems = (CartItems) ApiInvoker.deserialize(response, "", CartItems.class);
        cartItemId = cartItems.cartItemList.get(0).cart_item_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if (success) {
            Log.d("Tang Ho", "Success");
        }
    }
}