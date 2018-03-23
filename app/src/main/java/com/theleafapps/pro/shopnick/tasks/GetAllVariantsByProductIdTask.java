package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.Variant;
import com.theleafapps.pro.shopnick.models.multiples.Variants;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 21/06/16.
 */
public class GetAllVariantsByProductIdTask extends BaseAsyncRequest {

    public Variants variantsRec;
    public List<Variant> variantList;
    Context context;
    int productId;

    public GetAllVariantsByProductIdTask(Context context, int productId) {
        this.context = context;
        this.productId = productId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getAllVariantsByProductId";

        serviceName = AppConstants.DB_SVC;
        endPoint = "variant";
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
        //Log.d("Tang Ho"," >>>>> " + response);
        variantsRec =
                (Variants) ApiInvoker.deserialize(response, "", Variants.class);
        if (variantsRec != null)
            variantList = variantsRec.variants;
    }

    @Override
    protected void onCompletion(boolean success) {
        if (success && variantsRec != null) {
            Log.d("Tang Ho", " >>>>> Successfully Received Variants By ProductId ->" + productId);
        }
    }

}
