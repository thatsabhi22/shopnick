package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.Coupon;
import com.theleafapps.pro.shopnick.models.multiples.Coupons;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.HashMap;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 20/07/16.
 */
public class GetCouponByCodeTask extends BaseAsyncRequest {

    public Coupons couponsRec;
    public Coupon couponRec;
    Context context;
    int couponId;
    String couponCode;

    public GetCouponByCodeTask(Context context, String couponCode) {
        this.context = context;
        this.couponCode = couponCode;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException {
        callerName = "getCouponByCode";

        serviceName = AppConstants.DB_SVC;
        endPoint = "coupon";
        verb = "GET";

        // filter to only select the contacts in this group
        queryParams = new HashMap<>();
        queryParams.put("filter", "coupon_code=" + couponCode);

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
        couponsRec =
                (Coupons) ApiInvoker.deserialize(response, "", Coupons.class);
        if (couponsRec.coupons.size() > 0)
            couponRec = couponsRec.coupons.get(0);
    }

    @Override
    protected void onCompletion(boolean success) {
        if (success && couponsRec != null) {
            Log.d("Tang Ho", " >>>>> Success");
        }
    }

}
