package com.theleafapps.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 30/06/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CartItem extends BaseRecord {

    @JsonProperty("cart_item_id")
    public int cart_item_id;

    @JsonProperty("customer_id")
    public int customer_id;

    @JsonProperty("product_id")
    public int product_id;

    @JsonProperty("customer_dev_id")
    public String customer_dev_id;

    @JsonProperty("quantity")
    public int quantity;

    @JsonProperty("variant")
    public String variant;

    @JsonProperty("product_by_product_id")
    public Product product;
}
