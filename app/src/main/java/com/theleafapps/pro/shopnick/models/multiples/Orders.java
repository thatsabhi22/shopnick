package com.theleafapps.pro.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.pro.shopnick.models.BaseRecord;
import com.theleafapps.pro.shopnick.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 30/06/16.
 */
public class Orders extends BaseRecord {

    @JsonProperty("resource")
    public List<Order> orders = new ArrayList<>();

}
