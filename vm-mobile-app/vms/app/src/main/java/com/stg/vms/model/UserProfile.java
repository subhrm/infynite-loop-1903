package com.stg.vms.model;

import java.util.Date;

public class UserProfile {
    private long userId;
    private String userName;
    private String email;
    private String userRole;
    private Date loginTime;

    public UserProfile() {
    }

    public UserProfile(long userId, String userName, String email, String userRole, Date loginTime) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.userRole = userRole;
        this.loginTime = loginTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}