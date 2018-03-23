package com.theleafapps.pro.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 19/06/16.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductImage extends BaseRecord {

    @JsonProperty("product_image_id")
    public int product_image_id;

    @JsonProperty("product_id")
    public int product_id;

    @JsonProperty("image_url")
    public String image_url;

}
