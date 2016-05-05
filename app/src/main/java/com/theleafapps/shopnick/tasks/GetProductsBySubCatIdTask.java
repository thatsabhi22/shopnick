package com.theleafapps.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.shopnick.models.multiples.Products;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 04/05/16.
 */
public class GetProductsBySubCatIdTask extends BaseAsyncRequest {

    Context context;
    public Products productsRec;
    int subCatId;

    public GetProductsBySubCatIdTask(Context context, int subCataId){
        this.context = context;
        this.subCatId = subCataId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getProductsBySubCatId";

        serviceName = AppConstants.DB_SVC;
        endPoint = "product";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "sub_category_id=" + subCatId);
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
        Log.d("Tang Ho"," >>>>> " + response);
        productsRec =
                (Products) ApiInvoker.deserialize(response, "", Products.class);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && productsRec != null && productsRec.products.size() > 0){
            Log.d("Tang Ho"," >>>>> Success");
        }
    }
}
