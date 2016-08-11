package com.theleafapps.pro.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.pro.shopnick.models.BaseRecord;
import com.theleafapps.pro.shopnick.models.OrderDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 30/06/16.
 */
public class OrderDetailsMulti extends BaseRecord {

    @JsonProperty("resource")
    public List<OrderDetails> orders = new ArrayList<>();

}
