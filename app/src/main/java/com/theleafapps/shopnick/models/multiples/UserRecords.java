package com.theleafapps.shopnick.models.multiples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theleafapps.shopnick.models.BaseRecord;
import com.theleafapps.shopnick.models.UserRecord;

import java.util.ArrayList;
import java.util.List;

public class UserRecords extends BaseRecord {
    @JsonProperty("resource")
    public List<UserRecord> userRecord = new ArrayList<>();
}
