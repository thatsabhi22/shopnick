package com.theleafapps.shopnick.tasks;

import android.content.Context;

import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 08/07/16.
 */
public class GetAllCartItemTask extends BaseAsyncRequest {

    public CartItems cartItemsReceived;
    Context context;
    int customerId;

    public GetAllCartItemTask(Context context, int customer_id){
        this.context       =   context;
        this.customerId    =   customer_id;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getAllCartItem";

        serviceName = AppConstants.DB_SVC;
        endPoint = "cart_item";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "customer_id=" + customerId);
        queryParams.put("order", "cart_item_id%20ASC");

        // request without related would return just {id, contact_group_id, contact_id}
        // set the related field to go get the contact mRecordsList referenced by
        // each contact_group_relationship record
         queryParams.put("related", "product_by_product_id");

        // need to include the API key and session token
        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {
        //Log.d("Tang Ho"," >>>>> " + response);
        cartItemsReceived =
                (CartItems) ApiInvoker.deserialize(response, "", CartItems.class);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && cartItemsReceived != null && cartItemsReceived.cartItemList.size() > 0){

        }
    }
}
