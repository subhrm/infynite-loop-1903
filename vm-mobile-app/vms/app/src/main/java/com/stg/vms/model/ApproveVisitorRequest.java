package com.stg.vms.model;

public class ApproveVisitorRequest {
    private long visitorId;
    private String visitorPhoto;
    private long securityId;

    public ApproveVisitorRequest() {
    }

    public ApproveVisitorRequest(long visitorId, String visitorPhoto, long securityId) {
        this.visitorId = visitorId;
        this.visitorPhoto = visitorPhoto;
        this.securityId = securityId;
    }

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorPhoto() {
        return visitorPhoto;
    }

    public void setVisitorPhoto(String visitorPhoto) {
        this.visitorPhoto = visitorPhoto;
    }

    public long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(long securityId) {
        this.securityId = securityId;
    }
}