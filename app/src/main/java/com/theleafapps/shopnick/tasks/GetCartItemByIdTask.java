package com.theleafapps.shopnick.tasks;

import android.content.Context;

import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 10/07/16.
 */
public class GetCartItemByIdTask extends BaseAsyncRequest {

    Context context;
    int cartItemId;

    public GetCartItemByIdTask(Context context, int cartItemId){
        this.context = context;
        this.cartItemId = cartItemId;
    }



}
