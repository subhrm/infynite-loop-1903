package com.stg.vms.model;

import com.google.gson.annotations.SerializedName;

public class SearchByPhotoResponse {
    @SerializedName("visitor_id")
    private long visitorId;

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }
}
