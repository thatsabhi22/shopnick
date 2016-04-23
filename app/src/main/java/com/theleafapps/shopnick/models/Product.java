package com.theleafapps.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 22/04/16.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product extends BaseRecord{

    @JsonProperty("product_id")
    public int product_id;

    @JsonProperty("sub_category_id")
    public int sub_category_id;

    @JsonProperty("product_name")
    public String product_name;

    @JsonProperty("product_desc")
    public String product_desc;

    @JsonProperty("image_url")
    public String image_url;

    @JsonProperty("unit_mrp")
    public float unit_mrp;

    @JsonProperty("unit_offerprice")
    public float unit_offerprice;

    @Override
    public void setAllNonNull() {

        product_name = getNonNull(product_name);
        product_desc = getNonNull(product_desc);
        image_url = getNonNull(image_url);

    }

}

