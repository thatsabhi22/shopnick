package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.Products;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 23/04/16.
 */
public class AddProductTask extends BaseAsyncRequest {

    Context context;
    Products products;
    private int productId;

    public AddProductTask(Context context, Products products) {
        this.context = context;
        this.products = products;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "AddProductTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "product";

        verb = "POST";

        requestString = ApiInvoker.serialize(products);

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        // response has whole contact record, but we just want the id
        Products productsList = (Products) ApiInvoker.deserialize(response, "", Products.class);
        productId = productsList.products.get(0).product_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if (success) {
            Log.d("Tang Ho", "Success");
        }
    }

}
