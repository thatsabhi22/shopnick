package com.theleafapps.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.shopnick.models.BaseRecord;
import com.theleafapps.shopnick.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 22/04/16.
 */
public class SubCategories extends BaseRecord {

    @JsonProperty("resource")
    public List<SubCategory> subCategories = new ArrayList<>();
}
