package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 21/06/16.
 */
public class DeleteProductByIdTask extends BaseAsyncRequest {

    Context context;
    int product_id;

    public DeleteProductByIdTask(Context context, int product_id){
        this.product_id = product_id;
        this.context = context;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {

        callerName = "deleteProductByIdTask";
        serviceName = AppConstants.DB_SVC;
        endPoint = "product";

        verb = "DELETE";

//      filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "product_id=" + product_id);

        // need to include the API key and session token
        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);

    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {
        //Log.d("Tang Ho",response);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success)
            Log.d("Tang Ho", "Successfully Deleted Product with id ->" + product_id);
        }
}
