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
 * Created by aviator on 20/07/16.
 */
public class UpdateCustomerWalletValueTask extends BaseAsyncRequest {

    Context context;
    public int customerId;
    Customers customers;

    public UpdateCustomerWalletValueTask(Context context, Customers customers){
        this.context        =   context;
        this.customers      =   customers;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "UpdateCustomerWalletValue";

        serviceName = AppConstants.DB_SVC;
        endPoint = "customer";

        verb = "PUT";

        requestString = ApiInvoker.serialize(customers);

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        Customers customerList  =   (Customers) ApiInvoker.deserialize(response, "", Customers.class);
        customerId              =   customerList.customers.get(0).customer_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success) {
            Log.d("Tang Ho","Success");
        }
    }

}
