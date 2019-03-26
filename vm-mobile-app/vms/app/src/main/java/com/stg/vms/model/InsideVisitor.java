package com.stg.vms.model;

public class InsideVisitor {
    private long visitorId;
    private String visitorType;
    private String name;
    private String email;
    private String mobile;
    private long photoId;
    private String photoUrl;
    private String actualInTime;
    private String expectedOutTime;
    private int status;
    private String refName;
    private String refMobile;
    private String timeExceed;

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getActualInTime() {
        return actualInTime;
    }

    public void setActualInTime(String actualInTime) {
        this.actualInTime = actualInTime;
    }

    public String getExpectedOutTime() {
        return expectedOutTime;
    }

    public void setExpectedOutTime(String expectedOutTime) {
        this.expectedOutTime = expectedOutTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefMobile() {
        return refMobile;
    }

    public void setRefMobile(String refMobile) {
        this.refMobile = refMobile;
    }

    public String getTimeExceed() {
        return timeExceed;
    }

    public void setTimeExceed(String timeExceed) {
        this.timeExceed = timeExceed;
    }
}
