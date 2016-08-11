package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.Customers;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 18/07/16.
 */
public class AddCustomerTask extends BaseAsyncRequest {

    Context context;
    public int customerId;
    Customers customersObj;

    public AddCustomerTask(Context context, Customers customers){
        this.context        =   context;
        this.customersObj      =   customers;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "AddCustomerTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "customer";

        verb = "POST";

        requestString = ApiInvoker.serialize(customersObj).replace("\"customer_id\":0,","");
        requestString = requestString.replace(",\"customer_id\":0","");

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        // response has whole contact record, but we just want the id
        Customers customersObj   =   (Customers) ApiInvoker.deserialize(response, "", Customers.class);
        customerId               =   customersObj.customers.get(0).customer_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success) {
            Log.d("Tang Ho","Success");
        }
    }

}
