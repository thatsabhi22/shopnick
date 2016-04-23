package com.theleafapps.shopnick.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aviator on 22/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubCategory extends BaseRecord{

    @JsonProperty("sub_category_id")
    public int sub_category_id;

    @JsonProperty("category_id")
    public int category_id;

    @JsonProperty("sub_category_name")
    public String sub_category_name;

    @JsonProperty("sub_category_desc")
    public String sub_category_desc;

    @JsonProperty("sequence")
    public int sequence;

    @Override
    public void setAllNonNull() {

        sub_category_name = getNonNull(sub_category_name);
        sub_category_desc = getNonNull(sub_category_desc);

    }

}
