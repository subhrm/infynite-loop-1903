package com.stg.vms.model;

public class VisitorProfileResponse {
    private long visitorId;
    private String name;
    private String visitorType;
    private String email;
    private String mobile;
    private String photo;
    private String referredBy;
    private String expectedEntry;
    private String actualEntry;
    private String expectedExit;
    private int visitorStatus;

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getExpectedEntry() {
        return expectedEntry;
    }

    public void setExpectedEntry(String expectedEntry) {
        this.expectedEntry = expectedEntry;
    }

    public String getActualEntry() {
        return actualEntry;
    }

    public void setActualEntry(String actualEntry) {
        this.actualEntry = actualEntry;
    }

    public String getExpectedExit() {
        return expectedExit;
    }

    public void setExpectedExit(String expectedExit) {
        this.expectedExit = expectedExit;
    }

    public int getVisitorStatus() {
        return visitorStatus;
    }

    public void setVisitorStatus(int visitorStatus) {
        this.visitorStatus = visitorStatus;
    }
}
