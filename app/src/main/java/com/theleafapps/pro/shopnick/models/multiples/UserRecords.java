package com.theleafapps.pro.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.pro.shopnick.models.BaseRecord;
import com.theleafapps.pro.shopnick.models.UserRecord;

import java.util.ArrayList;
import java.util.List;

public class UserRecords extends BaseRecord {
    @JsonProperty("resource")
    public List<UserRecord> userRecord = new ArrayList<>();
}
