package com.theleafapps.pro.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 20/07/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Coupon extends BaseRecord {

    @JsonProperty("coupon_id")
    public int coupon_id;

    @JsonProperty("coupon_code")
    public String coupon_code;

    @JsonProperty("value")
    public String coupon_value;

    @JsonProperty("type")
    public String type;

    @JsonProperty("min_cart_value")
    public double min_cart_value;

    @Override
    public void setAllNonNull() {
        coupon_code = getNonNull(coupon_code);
        coupon_value = getNonNull(coupon_value);
        type = getNonNull(type);
    }
}
