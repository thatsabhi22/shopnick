package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.Product;
import com.theleafapps.pro.shopnick.models.multiples.Products;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;
import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 12/05/16.
 */
public class GetProductByIdTask extends BaseAsyncRequest {

    Context context;
    public Products productsRec;
    public Product productRec;
    int productId;

    public GetProductByIdTask(Context context, int productId){
        this.context = context;
        this.productId = productId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getProductById";

        serviceName = AppConstants.DB_SVC;
        endPoint = "product";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "product_id=" + productId);
//        queryParams.put("order", "sequence%20ASC");

        // request without related would return just {id, contact_group_id, contact_id}
        // set the related field to go get the contact mRecordsList referenced by
        // each contact_group_relationship record
        // queryParams.put("related", "contact_by_contact_id");

        // need to include the API key and session token
        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);

    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {
        //Log.d("Tang Ho"," >>>>> " + response);
        productsRec =
                (Products) ApiInvoker.deserialize(response, "", Products.class);
        if(productsRec.products.size()> 0)
            productRec = productsRec.products.get(0);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && productsRec != null){
            Log.d("Tang Ho"," >>>>> Success");
        }
    }

}
