package com.theleafapps.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.shopnick.models.Customer;
import com.theleafapps.shopnick.models.multiples.Customers;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 18/07/16.
 */
public class GetCustomerByCustomerDevIdTask extends BaseAsyncRequest{

    Context context;
    String customerDevId;
    public Customers customersRec;
    public Customer customerRec;

    public GetCustomerByCustomerDevIdTask(Context context, String customerDevId){
        this.context = context;
        this.customerDevId = customerDevId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getCustomerByCustomerDevId";

        serviceName = AppConstants.DB_SVC;
        endPoint = "customer";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "customer_dev_id=" + customerDevId);

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
        customersRec =
                (Customers) ApiInvoker.deserialize(response, "", Customers.class);
        if(customersRec.customers.size()> 0)
            customerRec = customersRec.customers.get(0);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && customersRec != null){
            Log.d("Tang Ho"," >>>>> Success");
        }
    }
}
