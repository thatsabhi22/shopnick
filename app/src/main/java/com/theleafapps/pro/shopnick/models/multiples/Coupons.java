package com.theleafapps.pro.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.pro.shopnick.models.BaseRecord;
import com.theleafapps.pro.shopnick.models.Coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 20/07/16.
 */
public class Coupons extends BaseRecord {

    @JsonProperty("resource")
    public List<Coupon> coupons = new ArrayList<>();

}
