package com.theleafapps.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.shopnick.models.BaseRecord;
import com.theleafapps.shopnick.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 30/06/16.
 */
public class CartItems extends BaseRecord {

    @JsonProperty("resource")
    public List<CartItem> cartItemList = new ArrayList<>();

}

