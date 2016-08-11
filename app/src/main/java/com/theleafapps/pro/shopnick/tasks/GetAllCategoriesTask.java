package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.Categories;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 22/04/16.
 */
public class GetAllCategoriesTask extends BaseAsyncRequest {

    public Categories categoriesReceived;
    Context context;

    public GetAllCategoriesTask(Context context){
            this.context = context;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getAllCategories";

        serviceName = AppConstants.DB_SVC;
        endPoint = "category";
        verb = "GET";

        // filter to only select the contacts in this group
            queryParams = new HashMap<>();
//            queryParams.put("filter", "SORT BY sequence");
            queryParams.put("order", "sequence%20ASC");

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
//        Log.d("Tang Ho"," >>>>> " + response);
        categoriesReceived =
                (Categories) ApiInvoker.deserialize(response, "", Categories.class);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && categoriesReceived != null && categoriesReceived.categories.size() > 0){
            Log.d("Tang Ho"," >>>>> AllCategories Received");
        }
    }
}
