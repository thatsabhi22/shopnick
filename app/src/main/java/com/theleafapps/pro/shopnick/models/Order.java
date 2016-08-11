package com.theleafapps.pro.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 30/06/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order extends BaseRecord {

    @JsonProperty("order_id")
    public int order_id;

    @JsonProperty("customer_id")
    public int customer_id;

    @JsonProperty("order_date")
    public String order_date;

    @JsonProperty("order_total_amount")
    public float order_total_amount;

    @JsonProperty("order_total_items")
    public int order_total_items;

    @JsonProperty("payment_type_code")
    public String payment_type_code;

    @JsonProperty("shipping_address")
    public String shipping_address;

    @JsonProperty("shipping_city")
    public String shipping_city;

    @JsonProperty("shipping_country")
    public String shipping_country;

    @JsonProperty("shipping_customer_name")
    public String shipping_customer_name;

    @JsonProperty("shipping_zipcode")
    public String shipping_zipcode;

}


