package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.SubCategory;
import com.theleafapps.pro.shopnick.models.multiples.SubCategories;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 06/08/16.
 */
public class GetSubCatByIdTask extends BaseAsyncRequest{


    Context context;
    public SubCategories subCategoriesRec;
    public SubCategory subCategoryRec;
    int subCatId;

    public GetSubCatByIdTask(Context context, int subCatId){
        this.context    = context;
        this.subCatId   = subCatId;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getSubCategoryById";

        serviceName = AppConstants.DB_SVC;
        endPoint = "sub_category";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "sub_category_id=" + subCatId);

        // need to include the API key and session token
        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);

    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {
        //Log.d("Tang Ho"," >>>>> " + response);
        subCategoriesRec =
                (SubCategories) ApiInvoker.deserialize(response, "", SubCategories.class);
        if(subCategoriesRec.subCategories.size()> 0)
            subCategoryRec = subCategoriesRec.subCategories.get(0);
    }

    @Override
    protected void onCompletion(boolean success) {
        if(success && subCategoriesRec != null){
            Log.d("Tang Ho"," >>>>> Success");
        }
    }

}
