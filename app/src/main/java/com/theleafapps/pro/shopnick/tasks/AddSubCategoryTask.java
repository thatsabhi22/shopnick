package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.SubCategories;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 23/04/16.
 */
public class AddSubCategoryTask extends BaseAsyncRequest {

        Context context;
        private int subCategoryId;
        SubCategories subCategories;

        public AddSubCategoryTask(Context context, SubCategories subCategories){
            this.context        =   context;
            this.subCategories    =   subCategories;
        }

        @Override
        protected void doSetup() throws ApiException {
            callerName = "AddSubCategoryTask";

            serviceName = AppConstants.DB_SVC;
            endPoint = "sub_category";

            verb = "POST";

            requestString = ApiInvoker.serialize(subCategories);

            applicationApiKey = AppConstants.API_KEY;
            sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
        }

        @Override
        protected void processResponse(String response) throws ApiException, org.json.JSONException {
            // response has whole contact record, but we just want the id
            SubCategories subCategoriesList = (SubCategories) ApiInvoker.deserialize(response, "", SubCategories.class);
            subCategoryId = subCategoriesList.subCategories.get(0).sub_category_id;
        }

        @Override
        protected void onCompletion(boolean success) {
            if(success) {
                Log.d("Tang Ho","Success");
            }
        }

}
