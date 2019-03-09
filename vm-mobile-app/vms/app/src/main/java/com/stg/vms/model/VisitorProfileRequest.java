package com.stg.vms.model;

public class VisitorProfileRequest {
    private String visitorId;
    private String securityRole;
    private int encrypted;

    public VisitorProfileRequest() {
    }

    public VisitorProfileRequest(String visitorId, String securityRole, int encrypted) {
        this.visitorId = visitorId;
        this.securityRole = securityRole;
        this.encrypted = encrypted;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(String securityRole) {
        this.securityRole = securityRole;
    }

    public int getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
    }
}
