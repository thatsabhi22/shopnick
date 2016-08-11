package com.theleafapps.pro.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.pro.shopnick.models.BaseRecord;
import com.theleafapps.pro.shopnick.models.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 18/07/16.
 */
public class Customers extends BaseRecord {

    @JsonProperty("resource")
    public List<Customer> customers = new ArrayList<>();
}
