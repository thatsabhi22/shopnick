package com.theleafapps.shopnick.tasks;

import android.content.Context;

import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 21/07/16.
 */
public class DeleteMultipleCartItemsTask extends BaseAsyncRequest {

    Context context;
    private JSONArray idArray;

    public DeleteMultipleCartItemsTask(Context context, JSONArray idArray){
        this.context = context;
        this.idArray = idArray;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "deleteMultipleCartItemsTask";
        serviceName = AppConstants.DB_SVC;
        endPoint = "cart_item";

        verb = "DELETE";

        // delete contact records by record id
        // need to remove '[' and ']' from JSON array because ids doesn't take JSON
        queryParams = new HashMap<>();
        queryParams.put("ids", idArray.toString().replace("[", "").replace("]", ""));

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success){
            //Toast.makeText(context,"Cart Items deleted",Toast.LENGTH_LONG).show();
        }
    }
}
