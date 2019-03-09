package com.stg.vms.model;

public class ApproveVisitorRequest {
    private String visitorId;
    private String visitorPhoto;

    public ApproveVisitorRequest() {
    }

    public ApproveVisitorRequest(String visitorId, String visitorPhoto) {
        this.visitorId = visitorId;
        this.visitorPhoto = visitorPhoto;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorPhoto() {
        return visitorPhoto;
    }

    public void setVisitorPhoto(String visitorPhoto) {
        this.visitorPhoto = visitorPhoto;
    }
}
