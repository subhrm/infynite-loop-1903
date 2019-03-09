package com.stg.vms.model;

public class LocationAccessRequest {
    private String visitorId;
    private long securityId;
    private int encrypted;

    public LocationAccessRequest() {
    }

    public LocationAccessRequest(String visitorId, long securityId, int encrypted) {
        this.visitorId = visitorId;
        this.securityId = securityId;
        this.encrypted = encrypted;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(long securityId) {
        this.securityId = securityId;
    }

    public int getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
    }
}
