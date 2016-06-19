package com.theleafapps.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.shopnick.models.ProductImage;
import com.theleafapps.shopnick.models.multiples.ProductImages;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 19/06/16.
 */
public class GetAllProductImagesByIdTask extends BaseAsyncRequest {

    Context context;
    public ProductImages productImagesRec;
    public ProductImage productImageRec;
    int productId;

    public GetAllProductImagesByIdTask(Context context, int productId){
        this.context = context;
        this.productId = productId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getProductImagesById";

        serviceName = AppConstants.DB_SVC;
        endPoint = "product_images";
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
        Log.d("Tang Ho"," >>>>> " + response);
        productImagesRec =
                (ProductImages) ApiInvoker.deserialize(response, "", ProductImages.class);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && productImagesRec != null){
            Log.d("Tang Ho"," >>>>> Success");
        }
    }

}
