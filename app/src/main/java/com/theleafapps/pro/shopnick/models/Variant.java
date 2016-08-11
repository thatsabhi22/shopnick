package com.theleafapps.pro.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 22/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Variant extends BaseRecord {

    @JsonProperty("variant_id")
    public int variant_id;

    @JsonProperty("product_id")
    public int product_id;

    @JsonProperty("variant_name")
    public String variant_name;

    @JsonProperty("variant_desc")
    public String variant_desc;

    @JsonProperty("available")
    public boolean available;


    @Override
    public void setAllNonNull() {

        variant_name = getNonNull(variant_name);
        variant_desc = getNonNull(variant_desc);

    }
}
