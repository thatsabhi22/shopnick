package com.theleafapps.shopnick.tasks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.theleafapps.shopnick.models.multiples.Categories;
import com.theleafapps.shopnick.ui.ShowcaseActivity;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 22/04/16.
 */

/*
*  private class RemoveContactsTask extends BaseAsyncRequest {
        private JSONArray idArray;
        public RemoveContactsTask(JSONArray idArray){
            this.idArray = idArray;
        }

        @Override
        protected void doSetup() throws ApiException, JSONException {
            try{
                // wait until all records referencing this one have been removed
                deleteSemaphore.acquire(2);
            }
            catch (Exception e){
                Log.e("contactListAdapter", "could not do final semaphore lock: " + e.toString());
                throw new ApiException(10, "could not lock the semaphore");
            }
            deleteSemaphore.release(2); // don't hold on to these guys

            callerName = "removeContactsTask";
            serviceName = AppConstants.DB_SVC;
            endPoint = "contact";

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
            removeContactsTask = null;
            if(success){
                int numRemoved = 0;
                for(int i = deleteSet.nextSetBit(0); i >= 0; i = deleteSet.nextSetBit(i + 1)) {
                    // calculate the new position of object following prev deletes
                    int realPosition = i - getNumHeaders(i) - numRemoved;
                    mRecordsList.remove(realPosition);
                    numRemoved++;
                }
                // once everything gets through successfully, reload the input views
                notifyDataSetChanged();
            }
        }
    }
*
* */
public class DeleteCategoryByIdTask extends BaseAsyncRequest {

    Context context;
    int category_id;

    public DeleteCategoryByIdTask(Context context, int category_id){
        this.category_id = category_id;
        this.context = context;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {

        callerName = "deleteCategoryByIdTask";
        serviceName = AppConstants.DB_SVC;
        endPoint = "category";

        verb = "DELETE";

//      filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "category_id=" + category_id);

        // need to include the API key and session token
        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);

    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {

        Log.d("Tang Ho",response);

    }

    @Override
    protected void onCompletion(boolean success) {

        if(success){
            Log.d("Tang Ho", "success");
        }
    }
}
