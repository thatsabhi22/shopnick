package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.Orders;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 30/06/16.
 */
public class AddNewOrderTask extends BaseAsyncRequest {

    Context context;
    public int orderId;
    Orders orders;

    public AddNewOrderTask(Context context, Orders orders){
        this.context   =   context;
        this.orders    =   orders;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "AddNewOrderTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "order_table";

        verb = "POST";

        requestString = ApiInvoker.serialize(orders);

        requestString = requestString.replace("\"order_id\":0,","");

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        // response has whole contact record, but we just want the id
        Orders ordersList   =   (Orders) ApiInvoker.deserialize(response, "", Orders.class);
        orderId             =   ordersList.orders.get(0).order_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success) {
            Log.d("Tang Ho","Success");
        }
    }
}
