package com.theleafapps.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.theleafapps.shopnick.models.BaseRecord;
import com.theleafapps.shopnick.models.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 19/06/16.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductImages extends BaseRecord{

    @JsonProperty("resource")
    public List<ProductImage> productImages = new ArrayList<>();

}
